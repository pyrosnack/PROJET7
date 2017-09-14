package com.glennou.PROJET7;

import com.sun.istack.internal.NotNull;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: pocthier.stanislas@gmail.com
 * Date: 14/09/2017
 */
public final class ReflectionGuiGeneration extends JPanel {

  public ReflectionGuiGeneration(@NotNull final Object o) {
    super();
    final Class classToGenerate = o.getClass();
    for (Field f : classToGenerate.getDeclaredFields()) {
      this.add(generateFieldGui(f, o));
    }
  }

  private Object getValueFromField(@NotNull final Field f,
                                   @NotNull final Object o) {
    try {
      return f.get(o);
    } catch (IllegalAccessException iae) {
      System.out.println(
              "Echec lors de l'accès à un champ dans la génération de gui pour la class "
                      + o.getClass().toString() + ", champ " + f.toString()
      );
      return null;
    }
  }

  private Component generateTabFromList(@NotNull final Field f,
                                        @NotNull final Object o) {
    assert(List.class.isAssignableFrom(f.getType()));
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    try {
      @SuppressWarnings("unchecked")
      List<Object> list = (List) f.get(o);
      if (list == null || list.size() == 0) {
        return table;
      }
      Object firstElement = list.get(0);
      Class classOfElements = firstElement.getClass();
      List<String> titres = Arrays.stream(classOfElements.getDeclaredFields())
              .map(Field::getName)
              .collect(Collectors.toList());
      model.setColumnIdentifiers(titres.toArray());
      list.forEach(
              element ->
                      model.addRow(
                              Arrays.stream(classOfElements.getDeclaredFields())
                                      .map(field -> getValueFromField(field, element))
                                      .toArray()
                      )
      );
    } catch (IllegalAccessException iae) {
      System.out.println(
              "Echec lors de l'accès à un champ dans la génération de gui pour la class "
                      + o.getClass().toString() + ", champ " + f.toString()
      );
    }
    return table;
  }

  private Component generateFieldGui(@NotNull final Field f,
                                     @NotNull final Object o) {
    if (List.class.isAssignableFrom(f.getType())) {
      return generateTabFromList(f, o);
    }
    String name = f.getName();
    try {
      Object value = f.get(o);
      String content = value != null ? value.toString() : "null";
      return new LabeledTextInput(name, content);
    } catch (IllegalAccessException iae) {
      System.out.println(
              "Echec lors de l'accès à un champ dans la génération de gui pour la class "
                      + o.getClass().toString() + ", champ " + f.toString()
      );
    }
    return null;
  }
}

class LabeledTextInput extends JPanel {
  public LabeledTextInput(String name, String content) {
    super();
    JTextField textField = new JTextField(content);
    JLabel label = new JLabel(name);
    this.add(label);
    this.add(textField);
    label.setLabelFor(textField);
  }
}
