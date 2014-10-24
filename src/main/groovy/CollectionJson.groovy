package org.groovy.collectionjson

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

  Items addLink(Links link){
    links << link
    this
  }
}

class Links {
  private String render = Render.LINK.string
  void setRender(Render renderType){
    this.render = renderType.string
  }
}

public enum Render{
  IMAGE("image"),
  LINK("link")

  final String string
  private Render(String string) {this.string = string}
}
