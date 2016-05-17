package nl.tue.database;

public class Table1 {
  private Long id;
  private Long source;
  private Long label;
  private Long target;

  public Table1(Long ID, Long Source, Long Label, Long Target)
  {
      this.id = ID;
      this.source = Source;
      this.label = Label;
      this.target = Target;
  }
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getSource() {
    return source;
  }

  public void setSource(Long source) {
    this.source = source;
  }

  public Long getLabel() {
    return label;
  }

  public void setLabel(Long label) {
    this.label = label;
  }

  public Long getTarget() {
    return target;
  }

  public void setTarget(Long target) {
    this.target = target;
  }
}
