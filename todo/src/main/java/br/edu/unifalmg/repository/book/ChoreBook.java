package br.edu.unifalmg.repository.book;

public class ChoreBook {

    public static final String FIND_ALL_CHORES = "SELECT * FROM db2020108017.chore";

    public static final String INSERT_CHORE = "INSERT INTO db2020108017.chore(description, isCompleted, deadline) VALUES (?,?,?);";

    public static final String UPDATE_CHORE = "UPDATE db2020108017.chores SET description=?, isCompleted=?, deadline=? WHERE id=?";


}
