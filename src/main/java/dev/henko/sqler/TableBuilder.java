package dev.henko.sqler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TableBuilder {

  private final String tableName;
  private final List<Element> elements;

  TableBuilder(String tableName) {
    this.tableName = tableName;
    this.elements = new LinkedList<>();
  }

  public TableBuilder element(Element element) {
    elements.add(element);
    return this;
  }

  public Table build() {
    return new Table(tableName, elements);
  }

}
