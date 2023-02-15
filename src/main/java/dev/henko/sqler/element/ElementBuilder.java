package dev.henko.sqler.element;

public class ElementBuilder {

  private final DataType dataType;
  private String colum;
  private Element.ForeignKey foreignKey;
  private Constraint[] constraints = {};

  public ElementBuilder(DataType dataType) {
    this.dataType = dataType;
  }

  public ElementBuilder colum(String colum) {
    this.colum = colum;
    return this;
  }

  public ElementBuilder foreignKey(Element.ForeignKey foreignKey) {
    this.foreignKey = foreignKey;
    return this;
  }

  public ElementBuilder constraints(Constraint[] constraints) {
    this.constraints = constraints;
    return this;
  }

  public Element build() {
    if(foreignKey != null) {
      return Element.create(colum, dataType, foreignKey, constraints);
    } else {
      return Element.create(colum, dataType, constraints);
    }
  }


}
