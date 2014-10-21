import groovy.json.JsonBuilder

class CollectionJson {
  def version
  def href
  def items = []

  String create() {
    JsonBuilder jsonBuilder = new JsonBuilder()
    jsonBuilder(collection : this)
    jsonBuilder.toString()
  }

  def addItems(Items item) {
    items << item
    this
  }
}

class Items {
  String href
  def data = []
  def links = []

  Items addData(Data dataObject){
    data << dataObject
    this
  }

  void addLink(Links link){
    links << link
  }
}

class Data {
  String prompt
  String name
  def value
}

class Links {

}
