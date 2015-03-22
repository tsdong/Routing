/*
package com.codeit.priorityrouting.DBSupport.JSONSupport

*/
/**
 * Created by FZDDFL on 3/3/2015.
 *//*





trait JSONSupport{

  val jackson = new ObjectMapper()
  jackson.registerModule(DefaultScalaModule)
  jackson.setSerializationInclusion(Include.NON_NULL)

  val m = ru.runtimeMirror(getClass.getClassLoader)
  def parseJson[A](s:String)(implicit tag:TypeTag[A]) =Try(fromJson(s))
  def fromJson[A](s:String)(implicit tag:TypeTag[A])=jackson.readValue(s,m.runtimeClass(typeOf[A].typeSymbol.asClass)).asInstanceOf[A]
  def toJson(a:Any)=jackson.writeValueAsString(a)


}
*/
