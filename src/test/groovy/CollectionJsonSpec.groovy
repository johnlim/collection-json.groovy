package org.collectionjason.groovy

import groovy.json.JsonSlurper
import spock.lang.*

class CollectionJsonSpec extends Specification {
  CollectionJson collectionJson
  JsonSlurper jsonSlurper

  def setup() {
    collectionJson = new CollectionJson()
    jsonSlurper = new JsonSlurper()
  }

  def "create should not return null"() {

    expect:
    collectionJson.create() != null
  }

  def "valid collection object should have version and href property"() {
    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.version == null
    result.collection.href == null
  }

  def "version should be customisable"() {
    collectionJson.version = "1.0"
    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.version == "1.0"
  }

  def "href should be customisable"() {
    collectionJson.href = "www.test.com"
    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.href == "www.test.com"
  }

  def "collection object MAY have an items array property"() {
    Items items = new Items()
    collectionJson.addItems(items)

    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.items[0] != null
  }

  def "collectionjson can add multiple items"() {
    Items items = new Items()
    collectionJson.addItems(items).addItems(items)

    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.items.size() == 2
  }

  def "items array should have href property"() {
    Items items = new Items(href: "www.items.com")
    collectionJson.addItems(items)

    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.items[0].href == "www.items.com"
  }

  def "items array may have data array child property"() {
    Items items = new Items()
    Data data = new Data()
    items.addData(data)

    expect:
    items.data[0] != null
  }

  def "items array can store multiple data objects"() {
    Items items = new Items()
    Data data = new Data()
    items.addData(data).addData(data)

    expect:
    items.data.size() == 2
  }

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

  def "A collection's items' data array can store objects with different value types"() {
    given: "that value property MAY contain one of the following data types: STRING, NUMBER, true, false or null"
    Data stringData = new Data(value: "string")
    Data integerData = new Data(value: 0)
    Data booleanData = new Data(value: false)
    Data nullData = new Data(value: null )
    Items item = new Items()
    item.addData(stringData).addData(integerData).addData(booleanData).addData(nullData)

    when: "collectionJson is created with the different value types"
    collectionJson.addItems(item)
    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    then: "the json constructed should contain the correct value types"
    int i = 0
    result.collection.items[0].data[i].value == "string"
    result.collection.items[0].data[++i].value == 0
    result.collection.items[0].data[++i].value == false
    result.collection.items[0].data[++i].value == null
  }

  def "Items array may have links array child property"() {
    Items items = new Items()
    Links link = new Links()
    items.addLink(link)

    expect:
    items.links != null
  }

}
