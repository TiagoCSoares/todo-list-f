package br.edu.unifalmg.repository.impl;

import br.edu.unifalmg.domain.Chore;
import br.edu.unifalmg.repository.ChoreRepository;
import br.edu.unifalmg.repository.book.ChoreBook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MySQLChoreRepository implements ChoreRepository {


        // Conectar no banco de dados
        private Connection connection;

        // Realiar oeprações no banco de dados
        // Executam em tempo de COMPILAÇÃO
        // Quando a consulta é executada uma única vez

        private Statement statement;
        // Realizar opera~]oes no banco de dados
        // Executar queries dinâmicas em tempo de execução
        // Ideal para quando vamos executar a query múltiplas vezes
        // Possui melhor performance quando comparado ao statement
        private PreparedStatement preparedStatement;
        // Utilizado para capturar o retorno de uma consulta
        // e.g.: Utilizei o statement para fazer
        private ResultSet resultSet;    //devolve a execução da consulta


        @Override
        public List<Chore> load() {
            if (!connectToMySQL()) {
                return new ArrayList<>();
            }
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(ChoreBook.FIND_ALL_CHORES);

                List<Chore> chores = new ArrayList<>();
                while(resultSet.next()) {
                    //Poderiamos ter criado a Chore usando o construtor completo
                    // OU poderiamos ter usado o construtor padrão + ter dado sets
                    Chore chore = Chore.builder()
                            .description(resultSet.getString("description"))
                            .isCompleted(resultSet.getBoolean("isCompleted"))
                            .deadline(resultSet.getDate("deadline").toLocalDate())
                            .build();
                    chores.add(chore);
                }
                return chores;
            } catch (SQLException exception) {
                System.out.println("Error when connecting to database.");
            }
            return null;
        }

        @Override
        public boolean saveAll(List<Chore> chores) {
            return false;
        }

        @Override
        public boolean save(Chore chore) {
            if (!connectToMySQL()){
                return Boolean.FALSE;
            }
            try {
                preparedStatement = connection.prepareStatement(
                        ChoreBook.INSERT_CHORE);
                preparedStatement.setString(1, chore.getDescription());
                preparedStatement.setBoolean(2, chore.getIsCompleted());
                preparedStatement.setDate(3, Date.valueOf(chore.getDeadline()));
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            } catch (SQLException exception) {
                System.out.println("Error when inserting a new chore on database");
            } finally {
                closeConnections();;
            }
            return  false;
        }


        private boolean connectToMySQL() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager
                        .getConnection("jdbc:mysql://192.168.1.254:3306/db2020108017?"
                                + "user=user2020108017&password=2020108017");
                return Boolean.TRUE;
            } catch (ClassNotFoundException | SQLException exception) {
                System.out.println("Error when connecting to database. Try again later");
            }
            return Boolean.FALSE;
        }

        private void closeConnections() {
           try {
               if (Objects.nonNull(connection) && !connection.isClosed()) {
                   connection.close();
               }
               if (Objects.nonNull(statement) && !statement.isClosed()) {
                   statement.close();
               }
               if (Objects.nonNull(preparedStatement) && !preparedStatement.isClosed()) {
                   preparedStatement.close();
               }
               if (Objects.nonNull(resultSet) && !resultSet.isClosed()) {
                   resultSet.close();
               }
           } catch (SQLException exception) {
               System.out.println("Error when closing database connections.");
           }
        }

    }

