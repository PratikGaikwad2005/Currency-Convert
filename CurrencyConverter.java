package currencyConvert;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurrencyConverter extends JFrame {
    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JTextField amountField;
    private JTextField resultField;

    public CurrencyConverter() {
        super("Currency Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // Use BorderLayout for main frame
        setLayout(new BorderLayout(10, 10));

        // ---------- Header ----------
        JLabel header = new JLabel("Currency Converter", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 32));
        add(header, BorderLayout.NORTH);

        // ---------- Center: Form Panel ----------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new TitledBorder("Convert Amount"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Amount
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Amount:"), gbc);
        amountField = new JTextField();
        amountField.setFont(amountField.getFont().deriveFont(18f));
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(amountField, gbc);

        // From Currency
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("From:"), gbc);
        fromCurrency = new JComboBox<>(CurrencyRates.getCurrencies());
        fromCurrency.setFont(fromCurrency.getFont().deriveFont(16f));
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        formPanel.add(fromCurrency, gbc);

        // To Currency
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("To:"), gbc);
        toCurrency = new JComboBox<>(CurrencyRates.getCurrencies());
        toCurrency.setFont(toCurrency.getFont().deriveFont(16f));
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
        formPanel.add(toCurrency, gbc);

        // Convert Button
        JButton convertBtn = new JButton("Convert");
        convertBtn.setFont(convertBtn.getFont().deriveFont(18f));
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 1;
        formPanel.add(convertBtn, gbc);

        // Result Field
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Result:"), gbc);
        resultField = new JTextField();
        resultField.setFont(resultField.getFont().deriveFont(18f));
        resultField.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 2;
        formPanel.add(resultField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // ---------- Footer: Status Bar ----------
        JLabel statusBar = new JLabel("Enter an amount and click Convert.", SwingConstants.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(statusBar, BorderLayout.SOUTH);

        // Action Listener
        convertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = convertCurrency();
                statusBar.setText(msg);
            }
        });
    }

    /**
     * Performs the conversion and updates the result field.
     * @return A status message for the status bar.
     */
    private String convertCurrency() {
        String amtText = amountField.getText().trim();
        try {
            double amount = Double.parseDouble(amtText);
            String from = (String) fromCurrency.getSelectedItem();
            String to   = (String) toCurrency.getSelectedItem();

            double rateFrom = CurrencyRates.getRate(from);
            double rateTo   = CurrencyRates.getRate(to);

            double usdValue = amount / rateFrom;
            double converted = usdValue * rateTo;

            String resultStr = String.format("%.2f %s", converted, to);
            resultField.setText(resultStr);
            return "Converted " + amount + " " + from + " → " + resultStr;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter a valid numeric amount.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );
            return "Error: invalid amount.";
        }
    }

    public static void main(String[] args) {
        // Ensure GUI is created on the EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CurrencyConverter frame = new CurrencyConverter();
                frame.setVisible(true);
            }
        });
    }
}
