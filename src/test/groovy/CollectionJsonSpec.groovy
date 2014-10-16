import groovy.json.JsonSlurper
import spock.lang.*

class CollectionJsonSpec extends Specification {

  JsonSlurper jsonSlurper

  def setup() {
    jsonSlurper = new JsonSlurper()
  }

  def "create should not return null"() {
    CollectionJson collectionJson = new CollectionJson()

    expect:
    collectionJson.create() != null
  }

  def "valid collection object should have version and href property"() {
    CollectionJson collectionJson = new CollectionJson()
    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.version == null
    result.collection.href == null
  }

  def "version should be customisable"() {
    CollectionJson collectionJson = new CollectionJson(version: "1.0")
    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.version == "1.0"
  }

  def "href should be customisable"() {
    CollectionJson collectionJson = new CollectionJson(href : "www.test.com")
    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.href == "www.test.com"
  }

  def "should enable populating items"() {
    CollectionJson collectionJson = new CollectionJson()
//    Items items = new Items(href: data: links: )
    Items items = new Items()
    collectionJson.populateItems(items)

    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.items[0] != null
  }

}
