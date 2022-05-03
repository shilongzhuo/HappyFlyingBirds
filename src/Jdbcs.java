import java.sql.*;


import javax.swing.JOptionPane;

public class Jdbcs {
    Connection con = null;
    Statement statement = null;
    ResultSet res = null;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/user?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    String name = "root";
    String passwd = "zhuoshilong";

    public Jdbcs() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, name, passwd);
            statement = con.createStatement();

        } catch (ClassNotFoundException e) {
            System.out.println("�Բ����Ҳ������Driver");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //���û���Ϣ���޸�ʵ���Ͼ��Ƕ�������޸�
    public boolean update(String username1, String password1, String newpassword) {
        boolean judge = false;
        boolean s = compare(username1, password1);
        if (s) {
            String sql = "update user set password=\"" + newpassword + "\"where username=\"" + username1 + "\"";
            try {

                int a = statement.executeUpdate(sql);
                System.out.println(111);
                if (a == 1) {
                    JOptionPane.showMessageDialog(null, "�����޸ĳɹ���");
                    judge = true;
                }
                con.close();
                statement.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "�û������ڣ�");
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "�޸�ʧ��");
        }
        return judge;
    }

    //ɾ���û���Ϣ
    public void delete(String username, String password) {
        if (compare(username, password)) {
            JOptionPane.showMessageDialog(null, "�Ѿ����ɾ��");
        } else {
            return;
        }
        String sql = "delete from user where username=\"" + username + "\"";
        try {
            int a = statement.executeUpdate(sql);
            con.close();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "�����ڸ��û���");
            e.printStackTrace();
        }

    }

    //�û�ע�Ṧ�ܵ�ʵ�֣��������
    public void insert(String username, String password) {
        if (username == null || username.trim().length() <= 0) {
            JOptionPane.showMessageDialog(null, "ע���˺�Ϊ�գ����������룡");
            return;
        }
        String sql = "insert into user(username,password,cards) values(\"" + username + "\",\"" + password + "\",0)";
        try {
            int a = statement.executeUpdate(sql);
            con.close();
            statement.close();
            if (a == 1) {
                JOptionPane.showMessageDialog(null, "ע��ɹ���");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "�Բ�����û����Ѿ����ˣ�");
            e.printStackTrace();
        }
    }

    //�Ա��û����������ǲ�ƥ��
    public boolean compare(String username, String password) {
        boolean m = false;
        String sql = "select password from user where username=\"" + username + "\"";
        try {
            res = statement.executeQuery(sql);
            if (res.next()) {
                String pa = res.getString(1);
                System.out.println(pa + " " + password);
                if (pa.equals(password)) {
                    m = true;
                } else {
                    JOptionPane.showMessageDialog(null, "�������");
                }
            } else {
                JOptionPane.showMessageDialog(null, "�û��������ڣ�");
            }
            res.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    public ResultSet sort() {
        String sql = "select * from user order by cards DESC";
        try {
            res = statement.executeQuery(sql);
            // ��������
//            while (res.next())
//            {
//                System.out.println("�û�����"+res.getString("username"));
//                System.out.println("����Ϊ��"+res.getString("cards"));
//            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return res;
    }

    public void updateCards(String username1, String password1, double newcards)//���·���
    {


        String sql = "update user set cards=\"" + newcards + "\"where username=\"" + username1 + "\"&&cards < \""+newcards+"\"||cards = NULL";
        try {
            int a = statement.executeUpdate(sql);
//            System.out.println(newcards);
//            System.out.println(a);
            if (a == 1) {

            }
            con.close();
            statement.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}