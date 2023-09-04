package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Hospede;

public class HospedeDAO {
	private Connection connection;

	public HospedeDAO(Connection connection) {
		this.connection = connection;
	}

	public void salvar(Hospede hospede) {
		try {
			String sql = "INSERT INTO Hospedes (Nome, Sobrenome, DataNascimento, Nacionalidade, Telefone) values (?, ?, ?, ?, ?)";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setString(1, hospede.getNome());
				preparedStatement.setString(2, hospede.getSobrenome());
				preparedStatement.setDate(3, hospede.getDataNascimento());
				;
				preparedStatement.setString(4, hospede.getNacionalidade());

				preparedStatement.execute();

				try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
					while (resultSet.next()) {
						hospede.setId(resultSet.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Hospede> listar() {
		try {
			List<Hospede> hospedes = new ArrayList<Hospede>();
			String sql = "SELECT * FROM Hospedes";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.execute();

				transformarResultSetEmHospede(hospedes, preparedStatement);
			}
			return hospedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Hospede buscar(String sobrenome) {
		Hospede hospede;
		String sql = "SELECT * FROM Hospedes WHERE Sobrenome = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, sobrenome);
			preparedStatement.execute();

			ResultSet resultSet = preparedStatement.getResultSet();
			hospede = new Hospede(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
					resultSet.getDate(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return hospede;
	}

	public void deletar(Integer id) {
		String sql = "DELETE ? FROM Hospedes WHERE Id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void alterar(String nome, String sobrenome, Date dataNascimento, String nacionalidade, String telefone,
			Integer id) {
		String sql = "UPDATE Hospedes HOS SET HOS.Nome = ?, HOS.Sobrenome = ?, HOS.DataNascimento = ?, HOS.nacionalidade, HOS.Telefone = ? WHERE Id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, nome);
			preparedStatement.setString(2, sobrenome);
			preparedStatement.setDate(3, dataNascimento);
			preparedStatement.setString(4, nacionalidade);
			preparedStatement.setString(5, telefone);
			preparedStatement.setInt(6, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void transformarResultSetEmHospede(List<Hospede> hospedes, PreparedStatement preparedStatement)
			throws SQLException {
		try (ResultSet resultSet = preparedStatement.getResultSet()) {
			while (resultSet.next()) {
				Hospede hospede = new Hospede(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getDate(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7));

				hospedes.add(hospede);
			}
		}
	}
}
