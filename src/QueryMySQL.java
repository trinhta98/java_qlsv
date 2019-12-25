import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryMySQL extends ConnectMySQL{
    public QueryMySQL() throws SQLException {
        new ConnectMySQL();
    }

    //kiểm tra trùng mã
    public boolean duplicate(String value) {
        try {
            String sql="select * from Student where MaSV=?";
            PreparedStatement preparedStatement=con.prepareStatement(sql);
            preparedStatement.setString(1, value);
            ResultSet resultSet=preparedStatement.executeQuery();
            return resultSet.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm
    public int insert(Student st) {
        try {
            String sql = "INSERT INTO Student(MaSV,HoTen,SDT) values(?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.executeQuery("SET NAMES 'UTF8'");
            preparedStatement.executeQuery("SET CHARACTER SET 'UTF8'");
            preparedStatement.setString(1, st.getMaSV());
            preparedStatement.setString(2, st.getHoTen());
            preparedStatement.setString(3, st.getSdt());

            return preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }


    // sửa
    public int update(Student st) {
        try {
            String sql = "update Student set hoten=?, sdt=? where MaSV=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, st.getHoTen());
            preparedStatement.setString(2, st.getSdt());
            preparedStatement.setString(3, st.getMaSV());
            return preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    // xóa
    public int delete(Student st) {
        try {
            String sql="delete from Student where MaSV=?";
            PreparedStatement preparedStatement=con.prepareStatement(sql);
            preparedStatement.setString(1, st.getMaSV());
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    //Hiển thị
    public ArrayList<Student> displayJTable() {
        ArrayList<Student> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM  Student";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                Student st = new Student();
                st.setMaSV(resultSet.getString(1));
                st.setHoTen(resultSet.getString(2));
                st.setSdt(resultSet.getString(3));

                list.add(st);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }

    public ArrayList<Student> search(String key) {
        ArrayList<Student> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM  Student where MaSV=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, key);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                Student st = new Student();
                st.setMaSV(resultSet.getString(1));
                st.setHoTen(resultSet.getString(2));
                st.setSdt(resultSet.getString(3));

                list.add(st);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }
}
