/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlysach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class QLS extends javax.swing.JFrame {

    ArrayList<Sach> lstSach;
    ArrayList<TheLoai> lstTL;
    DefaultTableModel model;
    int index;

    public QLS() {
        initComponents();
        setLocationRelativeTo(null);
        lstSach = new ArrayList<>();
        lstTL = new ArrayList<>();
        model = (DefaultTableModel) tblSach.getModel();
        lstSach = getAllSach();
        lstTL = getAllTheLoai();
        fillToTable(lstSach);
        fillToCB();
    }

    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String user = "UserJava3";
            String pass = "1234567";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QLSach";
            return DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QLS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(QLS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Sach> getAllSach() {
        ArrayList<Sach> list = new ArrayList<>();
        String sql = "SELECT * FROM SACH";
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
//                Masach nvarchar(20)not null,
//	TenSach nvarchar(50) not null,
//	NXB nvarchar(50) not null,
//	Sotrang int ,
//	SoLuong int ,
//	Giatien money ,
//	NgayNhap datetime,
//	vitridat nvarchar(30),
//	MaTheLoai nvarchar(15) not null,
//String maS, String tenS, String NXB, int soTrang, int soLuong, int giaTien, Date ngayNhap, String vtDat, String maTL
                list.add(new Sach(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getDate(7), rs.getString(8), rs.getString(9)));

            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(QLS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<TheLoai> getAllTheLoai() {
        ArrayList<TheLoai> list = new ArrayList<>();
        String sql = "SELECT * FROM TheLoai";
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                list.add(new TheLoai(rs.getString(1), rs.getString(2)));

            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(QLS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<Sach> getAllSachTL(String idTL) {
        ArrayList<Sach> list = new ArrayList<>();
        String sql = "SELECT * FROM SACH WHERE MaTheLoai LIKE '" + idTL + "'";
        System.out.println(sql);
        try {
            Statement statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                list.add(new Sach(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getDate(7), rs.getString(8), rs.getString(9)));

            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(QLS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void fillToTable(ArrayList<Sach> data) {
        model.setRowCount(0);
        for (Sach s : data) {
            model.addRow(new Object[]{s.getMaS(), s.getTenS(), s.getNXB(), s.getSoTrang(), s.getSoLuong(), new java.sql.Date(s.getNgayNhap().getTime()), s.getVtDat(), s.getGiaTien()});
        }
    }

    public void fillToCB() {
        String[] maTL = new String[lstTL.size()];
        for (int i = 0; i < lstTL.size(); i++) {
            maTL[i] = lstTL.get(i).getTenTL();
        }
        cboTK.setModel(new DefaultComboBoxModel<>(maTL));
        cboTL.setModel(new DefaultComboBoxModel<>(maTL));
    }

    public int getIndexSach() {
        String ma = (String) model.getValueAt(index, 0);
        for (int i = 0; i < lstSach.size(); i++) {
            if (lstSach.get(i).getMaS().equalsIgnoreCase(ma)) {
                return i;
            }
        }
        return -1;
    }

    public String getTenTL(String maTL) {
        for (TheLoai tl : lstTL) {
            if (tl.getMaTL().equalsIgnoreCase(maTL)) {
                return tl.getTenTL();
            }
        }
        return "";
    }

    public String getMaTL(String tenTL) {
        for (TheLoai tl : lstTL) {
            if (tl.getTenTL().equalsIgnoreCase(tenTL)) {
                return tl.getMaTL();
            }
        }
        return "";
    }

    public void showdetail() {
        txtMa.setText(lstSach.get(index).getMaS());
        txtTen.setText(lstSach.get(index).getTenS());
        txtNXB.setText(lstSach.get(index).getNXB());
        txtSotrang.setText(lstSach.get(index).getSoTrang() + "");
        txtSL.setText(lstSach.get(index).getSoLuong() + "");
        txtNgayNhap.setText(new java.sql.Date(lstSach.get(index).getNgayNhap().getTime()) + "");
        txtGT.setText(lstSach.get(index).getGiaTien() + "");
        txtVTD.setText(lstSach.get(index).getVtDat());
        cboTL.setSelectedItem(getTenTL(lstSach.get(index).getMaTL()));
        txtMa.setEditable(false);
        txtMa.setEnabled(false);
    }

    public void clearForm() {
        txtMa.setText("");
        txtTen.setText("");
        txtNXB.setText("");
        txtSotrang.setText("");
        txtSL.setText("");
        txtNgayNhap.setText("");
        txtGT.setText("");
        txtVTD.setText("");
        cboTL.setSelectedIndex(0);
        txtMa.setEditable(true);
        txtMa.setEnabled(true);
    }

    public Sach readForm() {
//        String maS, String tenS, String NXB, int soTrang, int soLuong, int giaTien, Date ngayNhap, String vtDat, String maTL
        String maS = txtMa.getText();
        String tenS = txtTen.getText();
        String NXB = txtNXB.getText();
        int soTrang = Integer.parseInt(txtSotrang.getText());
        int soLuong = Integer.parseInt(txtSL.getText());
        int giaTien = Integer.parseInt(txtGT.getText());
        Date ngayNhap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ngayNhap = sdf.parse(txtNgayNhap.getText());
        } catch (ParseException ex) {
            Logger.getLogger(QLS.class.getName()).log(Level.SEVERE, null, ex);
            ngayNhap = null;
        }
        String vtDat = txtVTD.getText();
        String maTL = getMaTL(cboTL.getSelectedItem() + "");
        return new Sach(maS, tenS, NXB, soTrang, soLuong, giaTien, ngayNhap, vtDat, maTL);
    }

    public void addSach() {
        Sach s = readForm();
        String sql = "insert into SACH(Masach,TenSach,NXB,Sotrang,SoLuong,Giatien,NgayNhap,vitridat,MaTheLoai) values (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = getConnection().prepareCall(sql);
            ps.setString(1, s.getMaS());
            ps.setString(2, s.getTenS());
            ps.setString(3, s.getNXB());
            ps.setInt(4, s.getSoTrang());
            ps.setInt(5, s.getSoLuong());
            ps.setInt(6, s.getSoLuong());
            ps.setDate(7, new java.sql.Date(s.getNgayNhap().getTime()));
            ps.setString(8, s.getVtDat());
            ps.setString(9, s.getMaTL());
            int chon = ps.executeUpdate();
            if (chon > 0) {
                lstSach = getAllSach();
                fillToTable(getAllSach());
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QLS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateSach() {
        Sach s = readForm();
        String sql = "update SACH set TenSach = ?,NXB =?,Sotrang=?,SoLuong=?,Giatien=?,NgayNhap=?,vitridat=?,MaTheLoai=? where Masach like '" + s.getMaS() + "'";
        try {
            PreparedStatement ps = getConnection().prepareCall(sql);
            ps.setString(1, s.getTenS());
            ps.setString(2, s.getNXB());
            ps.setInt(3, s.getSoTrang());
            ps.setInt(4, s.getSoLuong());
            ps.setInt(5, s.getSoLuong());
            ps.setDate(6, new java.sql.Date(s.getNgayNhap().getTime()));
            ps.setString(7, s.getVtDat());
            ps.setString(8, s.getMaTL());
            int chon = ps.executeUpdate();
            if (chon > 0) {
                 lstSach = getAllSach();
                fillToTable(getAllSach());
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QLS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteSach() {
        Sach s = readForm();
        String sql = "delete SACH where Masach like '" + txtMa.getText() + "'";
        try {
            Statement st = getConnection().createStatement();
            int chon = st.executeUpdate(sql);
            if (chon > 0) {
                 lstSach = getAllSach();
                fillToTable(getAllSach());
                JOptionPane.showMessageDialog(this, "Xóa thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QLS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean check() {
        if (txtMa.getText().trim().equals("") || txtTen.getText().trim().equals("") || txtNXB.getText().trim().equals("") || txtSotrang.getText().trim().equals("") || txtSL.getText().trim().equals("") || txtGT.getText().trim().equals("") || txtNgayNhap.getText().trim().equals("") || txtVTD.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Không để trông");
            return false;
        } else {
            Date ngayNhap = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                ngayNhap = sdf.parse(txtNgayNhap.getText());
                try {
                    int soTrang = Integer.parseInt(txtSotrang.getText());
                    int soLuong = Integer.parseInt(txtSL.getText());
                    int giaTien = Integer.parseInt(txtGT.getText());
                    if (soTrang < 0 || soLuong < 0 || giaTien < 0) {
                        JOptionPane.showMessageDialog(this, "Số phải lớn hơn 0");
                        return false;
                    } else {
                        return true;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Sai định dạng số");
                    return false;
                }
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Sai định dạng ngày");
                return false;
            }
        }
    }

    public boolean checkSach(String maS) {
        for (Sach s : lstSach) {
            if(s.getMaS().equalsIgnoreCase(maS)){
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        txtNXB = new javax.swing.JTextField();
        txtSotrang = new javax.swing.JTextField();
        txtSL = new javax.swing.JTextField();
        txtNgayNhap = new javax.swing.JTextField();
        txtVTD = new javax.swing.JTextField();
        cboTL = new javax.swing.JComboBox<>();
        btnAdd = new javax.swing.JButton();
        btnUPdate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        cboTK = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtGT = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Mã sách:");

        jLabel2.setText("Tên sách:");

        jLabel3.setText("NXB:");

        jLabel4.setText("Ngày nhập:");

        jLabel5.setText("Thể loại:");

        jLabel6.setText("Số trang:");

        jLabel7.setText("Vị trí đặt:");

        jLabel8.setText("Số lượng:");

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUPdate.setText("Update");
        btnUPdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUPdateActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel9.setText("Thể loại:");

        cboTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboTKMouseClicked(evt);
            }
        });
        cboTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTKActionPerformed(evt);
            }
        });

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sách", "Tên sách", "NXB", "Số trang", "Số lượng", "Ngày nhập", "Vị trí", "Giá tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSach);

        jLabel10.setText("Giá tiền:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addGap(19, 19, 19)
                        .addComponent(btnUPdate)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSotrang, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNXB, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cboTL, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtGT, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(btnDelete)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(btnClear))
                                                    .addComponent(txtVTD, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboTK, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(22, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSotrang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNXB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVTD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTL, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnUPdate)
                        .addComponent(btnAdd))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnClear)
                        .addComponent(btnDelete)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try {
            if (check()) {
                if(checkSach(txtMa.getText())){
                    addSach();
                }else{
                     JOptionPane.showMessageDialog(this, "Mã sách đã tồn tại");
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUPdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUPdateActionPerformed
        try {
            if (check()) {
                if(checkSach(txtMa.getText())){
                   JOptionPane.showMessageDialog(this, "Không tồn tại");
                }else{
                    updateSach();
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnUPdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            int chon = JOptionPane.showConfirmDialog(this, "Bạn muốn xóa");
            if(chon == 0){
                deleteSach();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
        try {
            index = tblSach.getSelectedRow();
            index = getIndexSach();
            showdetail();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tblSachMouseClicked

    private void cboTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboTKMouseClicked
       
    }//GEN-LAST:event_cboTKMouseClicked

    private void cboTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTKActionPerformed
         try {
            String maTL = getMaTL(cboTK.getSelectedItem()+"");
            
            fillToTable(getAllSachTL(maTL));
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cboTKActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        try {
            clearForm();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnClearActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QLS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUPdate;
    private javax.swing.JComboBox<String> cboTK;
    private javax.swing.JComboBox<String> cboTL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextField txtGT;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtNXB;
    private javax.swing.JTextField txtNgayNhap;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtSotrang;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtVTD;
    // End of variables declaration//GEN-END:variables
}
