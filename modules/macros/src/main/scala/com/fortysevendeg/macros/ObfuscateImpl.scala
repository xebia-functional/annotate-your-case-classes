package com.fortysevendeg.macros

import scala.annotation.{StaticAnnotation, tailrec}
import scala.language.experimental.macros
import scala.reflect.macros.whitebox._

class Obfuscate(fieldsToObfuscate: String*) extends StaticAnnotation {

  def macroTransform(annottees: Any*): Any = macro ObfuscateImpl.impl
}

object ObfuscateImpl {

  val sensitiveReplacementChar = '*'

  def replaceSensitiveValues(
      value: String,
      leaveUntouched: Set[Char] = Set('-'),
      maxTail: Int = 4,
      replacement: Char = sensitiveReplacementChar): String = {
    @tailrec
    def loop(chars: List[Char], acc: String = ""): String = chars match {
      case Nil => acc
      case h :: t =>

        val append = acc match {
          case _ if !leaveUntouched.contains(h) && value.length > (acc.length + maxTail) =>
            sensitiveReplacementChar
          case _ => h
        }
        loop(t, acc + append)
    }
    println(s"h = $value")
    loop(value.toCharArray.toList)
  }

  def impl(c: Context)
      (annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    def extractAnnotationParameters(tree: Tree): List[c.universe.Tree] = tree match {
      case q"new $name( ..$params )" => params
      case _ => throw new Exception("SensitiveInfo annotation must be have at least one parameter.")
    }

    def extractCaseClassesParts(classDecl: ClassDef) = classDecl match {
      case q"case class $className(..$fields) extends ..$parents { ..$body }" =>
        (className, fields, parents, body)
    }

    def replaceCaseClassSensitiveValues(tree: Tree) = tree match {
      case Literal(Constant(field: String)) =>
        val replacement =
          q"""
            ${TermName(field)} = com.fortysevendeg.macros.ObfuscateImpl.replaceSensitiveValues(this.${TermName(field)})
          """

        println(TermName(field))
        println(replacement)

        replacement
      case _ => c.abort(c.enclosingPosition, s"[replaceCaseClassSensitiveValues] Match error with $tree")
    }

    val sensitiveFields = extractAnnotationParameters(c.prefix.tree)

    println(s"aaaa: $sensitiveFields")

    val fieldReplacements = sensitiveFields map (replaceCaseClassSensitiveValues(_))

    def extractNewToString(sensitiveFields: List[Tree]) = {
      q"""
         override def toString: ${typeOf[String]} = {
          scala.runtime.ScalaRunTime._toString(this.copy(..$fieldReplacements))
         }
      """
    }

    def modifiedDeclaration(classDecl: ClassDef) = {
      val (className, fields, parents, body) = extractCaseClassesParts(classDecl)
      val newToString = extractNewToString(sensitiveFields)

      val params = fields.asInstanceOf[List[ValDef]] map {p => p.duplicate}

      val result = q"""
        case class $className ( ..$params ) extends ..$parents {
          $newToString
          ..$body
        }
      """

      c.Expr[Any](result)
    }

    annottees.map(_.tree).toList match {
      case (classDecl: ClassDef) :: Nil => modifiedDeclaration(classDecl)
      case _ => c.abort(c.enclosingPosition, "Invalid annottee")
    }
  }
}