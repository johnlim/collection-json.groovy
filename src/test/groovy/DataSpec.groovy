package org.groovy.collectionjson

import spock.lang.*

class DataSpec extends Specification {

  def "Data contain objects with name, value and prompt child properties"() {
    Data data = new Data(name: "data", value: "value", prompt: "prompt")

    expect:
    data != null
  }

  def "Data's name property should be of String data type"() {
    Data data = new Data()
    data.name = "string"

    expect:
    data.name == "string"
    data.name instanceof String
  }

  def "Data's prompt property should be of String data type"() {
    Data data = new Data()
    data.prompt = "string"

    expect:
    data.prompt == "string"
    data.prompt instanceof String
  }

  @Unroll("Data's value property MAY contain #inputValue")
  def "Data's value property  MAY contain one of the following data types: STRING NUMBER true false or null"(){
    when:
    Data data = new Data(value: inputValue)

    then:
    data.value == expected

    where:
    inputValue | expected
    "string"   | "string"
    0          | 0
    false      | false
    null       | null
  }
}
