package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Generador de JSON Aleatorio");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Panel para campos
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setBounds(20, 20, 550, 200);
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(fieldsPanel);
        scrollPane.setBounds(20, 20, 550, 200);
        frame.add(scrollPane);

        // Botón para añadir campos
        JButton addFieldButton = new JButton("Añadir Campo");
        addFieldButton.setBounds(20, 230, 150, 25);
        frame.add(addFieldButton);

        // Lista para almacenar los campos
        List<JTextField> fieldNameList = new ArrayList<>();
        List<JComboBox<String>> typeComboBoxList = new ArrayList<>();

        addFieldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField fieldName = new JTextField(15);
                JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"String", "Integer", "Boolean", "Double"});

                JPanel panel = new JPanel();
                panel.add(new JLabel("Nombre del campo:"));
                panel.add(fieldName);
                panel.add(new JLabel("Tipo de dato:"));
                panel.add(typeComboBox);

                fieldsPanel.add(panel);
                fieldsPanel.revalidate();
                fieldsPanel.repaint();

                fieldNameList.add(fieldName);
                typeComboBoxList.add(typeComboBox);
            }
        });

        // Cantidad de mapas
        JLabel countLabel = new JLabel("Cantidad de mapas:");
        countLabel.setBounds(20, 270, 150, 25);
        frame.add(countLabel);

        JTextField countField = new JTextField();
        countField.setBounds(180, 270, 180, 25);
        frame.add(countField);

        // Botón para generar JSON
        JButton generateButton = new JButton("Generar JSON");
        generateButton.setBounds(20, 310, 550, 25);
        frame.add(generateButton);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int count = Integer.parseInt(countField.getText());

                    if (fieldNameList.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Debe añadir al menos un campo.");
                        return;
                    }

                    JSONArray jsonArray = new JSONArray();
                    Random random = new Random();

                    for (int i = 0; i < count; i++) {
                        JSONObject jsonObject = new JSONObject();
                        for (int j = 0; j < fieldNameList.size(); j++) {
                            String field = fieldNameList.get(j).getText();
                            String type = (String) typeComboBoxList.get(j).getSelectedItem();

                            switch (type) {
                                case "Integer":
                                    jsonObject.put(field, random.nextInt(100));
                                    break;
                                case "String":
                                    jsonObject.put(field, "Valor" + random.nextInt(100));
                                    break;
                                case "Boolean":
                                    jsonObject.put(field, random.nextBoolean());
                                    break;
                                case "Double":
                                    jsonObject.put(field, random.nextDouble());
                                    break;
                            }
                        }
                        jsonArray.put(jsonObject);
                    }

                    try (FileWriter file = new FileWriter("datos.json")) {
                        file.write(jsonArray.toString(4)); // Escribe el JSON con una indentación de 4 espacios
                        JOptionPane.showMessageDialog(frame, "Archivo JSON creado con éxito.");
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(frame, "Error al escribir el archivo JSON: " + ioException.getMessage());
                    }

                } catch (NumberFormatException numberFormatException) {
                    JOptionPane.showMessageDialog(frame, "Por favor, introduzca un número válido para la cantidad de mapas.");
                }
            }
        });

        frame.setVisible(true);
    }
}
