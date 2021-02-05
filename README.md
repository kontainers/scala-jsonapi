# Description

[![Build Status](https://travis-ci.org/kontainers/scala-jsonapi.svg)](https://travis-ci.org/kontainers/scala-jsonapi)
[![codecov.io](https://codecov.io/github/kontainers/scala-jsonapi/coverage.svg?branch=master)](https://codecov.io/github/kontainers/scala-jsonapi?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.kontainers/scala-jsonapi_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.kontainers/scala-jsonapi_2.12)

scala-jsonapi is a Scala library that aims to help you produce JSON output based on the [JSON API specification](https://jsonapi.org/) easily and painlessly. The library is compatible with Scala versions `2.11`, `2.12` and `2.13`. It supports read and write for the following backends:

 * [Play-JSON](https://www.playframework.com/documentation/2.6.x/ScalaJson)
 * [Spray-JSON](https://github.com/spray/spray-json)


## Current Status

This library is very much a work in progress, so expect its API to change.

This is a fork of https://github.com/scala-jsonapi/scala-jsonapi, made with the intention of supporting play-json (and possibly some spray-json support).

# Setup

To use scala-jsonapi, first add a library dependency.

    libraryDependencies += "io.kontainers" %% "scala-jsonapi" % "0.9.0"

You also have to provide the used backend (e.g. play-json).

The library provides serialization and deserialization of JSON API root objects to JSON using either Spray-JSON or Play-JSON. Please note that you need to explicitly add a dependency to either spray-json or play-json to your project.

## Spray-JSON

    import org.zalando.jsonapi.json.sprayjson.SprayJsonJsonapiProtocol._
    import spray.json._

    // Serialization
    val rootObject: RootObject = ???
    rootObject.toJson

    // Deserialization
    val json: JsValue = ???
    json.convertTo[RootObject]

## Play-JSON

The JSON API support can then be imported using `PlayJsonJsonapiSupport` as follows

    import org.zalando.jsonapi.json.playjson.PlayJsonJsonapiSupport._
    import play.api.libs.json._

    // Serialization
    val rootObject: RootObject = ???
    Json.toJson(rootObject)

    // Deserialization
    val json: JsValue = ???
    Json.fromJson[RootObject](json)

## Creating a JSON API Root Object

scala-jsonapi provides type class `JsonapiRootObjectWriter` so that you can create a JSON API representation for your resources. The following code snippet demonstrates its usage:

    import org.zalando.jsonapi
    import jsonapi.Jsonapi

    case class Person(name: String)

    implicit val personJsonapiWriter = new JsonapiRootObjectWriter[Person] {
      override def toJsonapi(person: Person) = {
        ???
      }
    }

    val personRootObject: RootObject = Jsonapi.asJsonapi(Person("Boris M."))

In contrast there is a type class called `JsonapiRootObjectReader` that supports conversion from JSON API representation to your resources. To illustrate:

    import org.zalando.jsonapi
    import jsonapi.Jsonapi

    case class Person(name: String)

    implicit val personJsonapiReader = new JsonapiRootObjectReader[Person] {
      override def fromJsonapi(rootObject: RootObject) = {
        ???
      }
    }

    val person: Person = Jsonapi.fromJsonapi[Person](RootObject(...))

For complete usage, see [the specs example](src/test/scala/org/zalando/jsonapi/json/ExampleSpec.scala).

## JSON API Links Support

There is support for string and object links.

To create a string "self" link:

    Links.self("href", None)

To create an object "self" link:

    Links.self("href", Some(meta))

# License

scala-jsonapi is licensed under the [MIT license](LICENSE) unless otherwise stated in the license files in higher directories (if any).

