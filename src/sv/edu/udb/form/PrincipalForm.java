package sv.edu.udb.form;

public class PrincipalForm extends javax.swing.JFrame {

    public PrincipalForm() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ventana Principal");

        javax.swing.JLabel label = new javax.swing.JLabel("PrincipalForm funcionando correctamente");
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        getContentPane().add(label, java.awt.BorderLayout.CENTER);
        setSize(400, 300);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new PrincipalForm().setVisible(true);
        });
    }
}
