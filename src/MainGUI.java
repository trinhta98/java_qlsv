import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class MainGUI extends JFrame {
    JTextField txtMaSV = new JTextField(30);
    JTextField txtHoTen = new JTextField(30);
    JTextField txtSDT = new JTextField(30);
    JLabel lblMaSV = new JLabel("Mã sinh viên: ");
    JLabel lblHoTen = new JLabel("Họ tên: ");
    JLabel lblSDT = new JLabel("Số điện thoại: ");
    JButton btnThem = new JButton("Thêm");
    JButton btnCapnhat = new JButton("Cập nhật");
    JButton btnXoa = new JButton("Xóa");
    JButton btnTim = new JButton("Tìm");


    JPanel pnInput;

    DefaultTableModel myDefaultTableModel = new DefaultTableModel();
    JTable myJTable;

    protected void xuLyClick(){
        int row = myJTable.getSelectedRow();
        TableModel model = myJTable.getModel();

        txtMaSV.setText(model.getValueAt(row, 0).toString());
        txtHoTen.setText(model.getValueAt(row, 1).toString());
        txtSDT.setText(model.getValueAt(row, 2).toString());
    }

    private void addEvents(){
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add();
            }
        });

        btnCapnhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    refresh();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    delete();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    search();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        myJTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickTable();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    protected void clickTable(){
        int row = myJTable.getSelectedRow();
        TableModel model = myJTable.getModel();

        txtMaSV.setText(model.getValueAt(row, 1).toString());
        txtHoTen.setText(model.getValueAt(row, 2).toString());
        txtSDT.setText(model.getValueAt(row, 3).toString());
    }
    protected void add(){
        txtMaSV.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
    }

    protected void refresh() throws SQLException {
        if(txtMaSV.getText().equals("")){
            showList();
        }else{
            Student st = new Student();
            st.setMaSV(txtMaSV.getText());
            st.setHoTen(txtHoTen.getText());
            st.setSdt(txtSDT.getText());

            QueryMySQL queryMySQL = new QueryMySQL();
            if(queryMySQL.duplicate(txtMaSV.getText())){
                int rep1 = JOptionPane.showConfirmDialog(null, "Sinh viên này đã tồn tại vui lòng chọn mã sinh viên khác", "Thông báo", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("./src/icon/bell.png"));
            }else{
                int rep = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm sinh viên này?", "Thông báo!",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./src/icon/question.png"));
                if(JOptionPane.YES_NO_OPTION == rep){
                    int x = queryMySQL.insert(st);
                    if(x > 0){
                        JOptionPane.showMessageDialog(null, "Thêm thành công!!!","Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./src/icon/check.png"));
                        showList();
                    }else{
                        JOptionPane.showMessageDialog(null, "Thêm thất bạt!!!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./src/icon/dislike.png"));
                    }
                }
            }
        }
    }
    protected void delete() throws SQLException {
        int rep = JOptionPane.showConfirmDialog(null, "Bạn có thực sự muốn xóa", "Hỏi", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./src/icon/question.png"));
        if(rep == JOptionPane.YES_OPTION){
            Student st = new Student();
            st.setMaSV(txtMaSV.getText());

            QueryMySQL queryMySQL = new QueryMySQL();
            int x = queryMySQL.delete(st);
            if(x > 0){
                JOptionPane.showMessageDialog(null, "Xóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./src/icon/check.png"));
                showList();
            }else{
                JOptionPane.showMessageDialog(null, "Xóa thất bại!!!", "Thông báo", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("./src/icon/dislike.png"));
            }
        }
    }

    protected void search() throws SQLException {
        QueryMySQL queryMySQL = new QueryMySQL();
        ArrayList<Student> ds = queryMySQL.search(txtMaSV.getText());
        myDefaultTableModel.setRowCount(0);
        for(Student x : ds){
            Vector<Object> vec = new Vector<>();
            vec.add("STT");
            vec.add(x.getMaSV());
            vec.add(x.getHoTen());
            vec.add(x.getSdt());
            myDefaultTableModel.addRow(vec);
        }
    }

    protected void showList() throws SQLException {
        QueryMySQL queryMySQL = new QueryMySQL();
        ArrayList<Student> ds = queryMySQL.displayJTable();
        myDefaultTableModel.setRowCount(0);
        int count = 1;
        for(Student x : ds){
            Vector<Object> vec = new Vector<>();
            vec.add(count);
            vec.add(x.getMaSV());
            vec.add(x.getHoTen());
            vec.add(x.getSdt());
            myDefaultTableModel.addRow(vec);
            count++;
        }
    }

    // Phương thức tạo ra một mẫu dòng
    public JPanel addPanel(JLabel lbl, JTextField txt) {
        JPanel pn = new JPanel();
        pn.setLayout(new FlowLayout(FlowLayout.CENTER));
        pn.add(lbl);
        pn.add(txt);

        return pn;
    }

    private void addControls(){
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        JPanel pnDieuKhien = new JPanel();
        pnDieuKhien.setLayout(new BoxLayout(pnDieuKhien, BoxLayout.Y_AXIS));

        JPanel pnTieuDe = new JPanel();
        JLabel lblTieuDe = new JLabel("QUẢN LÝ SINH VIÊN");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));
        lblTieuDe.setForeground(Color.RED);
        pnTieuDe.add(lblTieuDe);
        pnDieuKhien.add(pnTieuDe);

        //border
        Border borderDieuKhien = BorderFactory.createLineBorder(Color.GREEN);
        TitledBorder titledBorderDieuKhien = new TitledBorder(borderDieuKhien, "Điều kiển");

        Border borderHienThi = BorderFactory.createLineBorder(Color.yellow);
        TitledBorder titledBorderHienThi = new TitledBorder(borderHienThi, "Hiển thị");

        //input
        pnInput = new JPanel();
        pnInput.setLayout(new BoxLayout(pnInput, BoxLayout.Y_AXIS));
        pnDieuKhien.add(pnInput);

        JPanel pnMaSV = addPanel(lblMaSV, txtMaSV);
        pnInput.add(pnMaSV);
        lblMaSV.setPreferredSize(lblSDT.getPreferredSize());

        JPanel pnHoTen = addPanel(lblHoTen, txtHoTen);
        pnInput.add(pnHoTen);
        lblHoTen.setPreferredSize(lblSDT.getPreferredSize());

        JPanel pnSDT = addPanel(lblSDT, txtSDT);
        pnInput.add(pnSDT);

        JPanel pnNutLenh = new JPanel();
        pnNutLenh.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnNutLenh.add(btnThem);
        pnNutLenh.add(btnCapnhat);
        pnNutLenh.add(btnXoa);
        pnNutLenh.add(btnTim);

        pnDieuKhien.add(pnNutLenh);
        container.add(pnDieuKhien);
        pnDieuKhien.setBorder(titledBorderDieuKhien);

        btnThem.setIcon(new ImageIcon("./src/icon/add-file.png"));
        btnCapnhat.setIcon(new ImageIcon("./src/icon/file-refresh.png"));
        btnXoa.setIcon(new ImageIcon("./src/icon/delete.png"));
        btnTim.setIcon(new ImageIcon("./src/icon/seo.png"));

        //bảng hiển thị
        JPanel pnHienThi = new JPanel();
        pnHienThi.setLayout(new BorderLayout());
        container.add(pnHienThi);
        pnHienThi.setBorder(titledBorderHienThi);

        myDefaultTableModel.addColumn("STT");
        myDefaultTableModel.addColumn("Mã sinh viên");
        myDefaultTableModel.addColumn("Tên sinh viên");
        myDefaultTableModel.addColumn("Số điện thoại");

        myJTable = new JTable(myDefaultTableModel);
        myJTable.getTableHeader().setForeground(Color.RED);
        myJTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane sc = new JScrollPane(myJTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pnHienThi.add(sc, BorderLayout.CENTER);
    }


    public MainGUI(String title) throws SQLException {
        this.setTitle(title);
        addControls();
        addEvents();
        showList();
    }

    public void showWindow() {
        this.setSize(800, 500);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
