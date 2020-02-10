package fmi.informatics.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/* При натискане на бутон да се даде възможност на потребителя да избере по коя колона да сортира
*	Създаване на диалог
*	Добавяне на текст за избор
*	Добавяне на текстово поле, където потребителя да въведе своя избор
*	Добавяне на бутон, който спрямо избора ще сортира съответната колона
*	Добавяне на Custom Comparators
*/
public class CustomDialog extends JDialog implements ActionListener, PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	
	private String Text;
	private String Text1;
	private JTextField textField;
	private JOptionPane optionPane;
	private JTextField textFieldOrder;

	
	private String okLabel = "Ok";
	private String cancelLabel = "Cancel";

	private PersonDataGUI parentGui;

	// Създаване на диалог
	public CustomDialog(String text, PersonDataGUI parent) {
		setTitle("Избор на сортиране");

		this.parentGui = parent;
		this.textField = new JTextField(2);

		// Създаване на масив с текст и с компоненти, които да се визуализират
		Object[] array = {text, textField};
		Object[] options = {okLabel, cancelLabel};

		optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE, 
				JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);

		// Визуализиране на диалога
		setContentPane(optionPane);
		
		// Прихващане на затварянето на диалога
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				// Вместо да затваряме директно прозореца, ще се промени стойността на JOptionPane
				optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
			}
		});

		// Уверяваме се, че текстовото поле винаги получава първия фокус
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				textField.requestFocusInWindow();
			}
		});

		// регистриране на event handler за текстовото поле
		textField.addActionListener(this);
		
		// регистриране на event handler, който реагира при промяна на състоянието на ОptionPane
		optionPane.addPropertyChangeListener(this);
	} // end CustomDialog constructor

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();

		// проверка, дали има промяна в ОptionPane стойността
		if (isVisible() && (evt.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(propertyName) || 
						JOptionPane.INPUT_VALUE_PROPERTY.equals(propertyName))) {
			
			Object value = optionPane.getValue();

			if (value == JOptionPane.UNINITIALIZED_VALUE) {
				return;
			}

			optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

			if (value.equals(okLabel)) {
				if (textField.getText() != null && !textField.getText().isEmpty() && textFieldOrder.getText() != null &&
				!textFieldOrder.getText().isEmpty()) {
					Text = textField.getText();
					Text1 = textFieldOrder.getText();
					int Value;
					int Value1;
					if(Text.matches("\\d") && Text1.matches("\\d")){
						Value = Integer.parseInt(Text);
						Value1 = Integer.parseInt(Text1);

						if (Value >= 1 && Value <= 5) {

							if (Value1 == 1 || Value1 == 2) {
								parentGui.sortTable(Value,Value1, parentGui.table, PersonDataGUI.people);

								clearAndHide();
							} else {
								// Текстът е невалиден
								textFieldOrder.selectAll();

								JOptionPane.showMessageDialog(CustomDialog.this,
										"Sorry value: " + Text1 + "in not correct",
										"Error", JOptionPane.ERROR_MESSAGE);

								Text1 = null;
								textFieldOrder.requestFocusInWindow();
							}

						} else {
							// Текстът е невалиден
							textField.selectAll();

							JOptionPane.showMessageDialog(CustomDialog.this,
									"Sorry: " + Text + "is not what expect",
									"Error", JOptionPane.ERROR_MESSAGE);

							Text = null;
							textField.requestFocusInWindow();
						}

					}
					else{
						textField.selectAll();
						textFieldOrder.selectAll();
						JOptionPane.showMessageDialog(CustomDialog.this,
								"Sorry the value is letter "
										+ "Zone 1: " + Text + "Zone 2: " + Text1,
								"Error", JOptionPane.ERROR_MESSAGE);

						Text = null;
						Text1 = null;

						textField.requestFocusInWindow();
						textFieldOrder.requestFocusInWindow();
					}



				} else {
					// Случай, в който потребителят не е въвел стойност
					JOptionPane.showMessageDialog(CustomDialog.this,
							"Enter value", 
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				Text = null;
				Text1 = null;
				clearAndHide();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		optionPane.setValue(okLabel);

	}

	// Този метод изчиства диалога и го скрива
	private void clearAndHide() {
		textField.setText(null);
		setVisible(false);
	}
}
