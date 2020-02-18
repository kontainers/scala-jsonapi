package org.zalando.jsonapi.json

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.zalando.jsonapi.json.sprayjson.SprayJsonJsonapiProtocol
import org.zalando.jsonapi.model.JsonApiObject.StringValue
import org.zalando.jsonapi.model.RootObject.ResourceObject
import org.zalando.jsonapi.model.{Attribute, Links, RootObject}
import org.zalando.jsonapi.{JsonapiRootObjectWriter, _}
import spray.json._

class ExampleSpec extends AnyWordSpec with Matchers with SprayJsonJsonapiProtocol {
  "JsonapiRootObject" when {
    "using root object serializer" must {
      "serialize accordingly" in {

        val json =
          """
          {
            "data": {
              "id": "42",
              "type": "person",
              "attributes": {
                "name": "foobar"
              }
            }
          }
          """.stripMargin.parseJson

        Person(42, "foobar").rootObject.toJson mustEqual json
      }
    }

    "serialize accordingly with links object in data object" in {
      implicit val personJsonapiRootObjectWriter: JsonapiRootObjectWriter[Person] = new JsonapiRootObjectWriter[Person] {
        override def toJsonapi(person: Person) = {
          RootObject(data = Some(ResourceObject(
            `type` = "person",
            id = Some(person.id.toString),
            attributes = Some(List(
              Attribute("name", StringValue(person.name))
            )), links = Some(List(Links.Self("http://test.link/person/42", None))))))
        }
      }

      val json =
        """
          {
            "data": {
              "id": "42",
              "type": "person",
              "attributes": {
                "name": "foobar"
              },
              "links": {
                "self": "http://test.link/person/42"
              }
            }
          }
        """.stripMargin.parseJson

      Person(42, "foobar").rootObject.toJson mustEqual json
    }

    "serialize accordingly with links object in root object" in {
      implicit val personJsonapiRootObjectWriter: JsonapiRootObjectWriter[Person] = new JsonapiRootObjectWriter[Person] {
        override def toJsonapi(person: Person) = {
          RootObject(data = Some(ResourceObject(
            `type` = "person",
            id = Some(person.id.toString),
            attributes = Some(List(
              Attribute("name", StringValue(person.name))
            )))), links = Some(List(Links.Next("http://test.link/person/43", None))))
        }
      }

      val json =
        """
          {
            "data": {
              "id": "42",
              "type": "person",
              "attributes": {
                "name": "foobar"
              }
            },
            "links": {
              "next": "http://test.link/person/43"
            }
          }
        """.stripMargin.parseJson

      Person(42, "foobar").rootObject.toJson mustEqual json
    }
  }
}