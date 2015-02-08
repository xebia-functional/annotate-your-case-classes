Annotate your Case Classes
---

We are sure that you have occasionally needed in some way applying a specific behavior on your classes, even more if we are talking about our domain model.

Why the Scala macros could help us? Let us focus on an individual case to find a real motivation. Let us assume that your application handles sensitive information and many of our case classes have been modeled with critical data.

How can we deal with this scenery? Should we separate among two types of classes type? Should we override `toString` method in all our classes in order to prevent show sensitive data in our log system? Of course not. There are several ways to accomplish it and one of them is use annotations over our classes, for example. Something like this could be a good deal:

```
@ToStringObfuscate("password")
case class TestWithObfuscation(username : String, password : String)
```

This repo brings an example about how to develop a `ToStringObfuscate` tag with Scala macros.

License
======

Copyright (C) 2012 47 Degrees, LLC http://47deg.com hello@47deg.com

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.