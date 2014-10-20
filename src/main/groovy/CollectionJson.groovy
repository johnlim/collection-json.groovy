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

  Items addData(Data dataObject){
    data << dataObject
    this
  }
}

class Data {

}
