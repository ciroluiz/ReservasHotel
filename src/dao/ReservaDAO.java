package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Reserva;

public class ReservaDAO {
	private Connection connection;

	public ReservaDAO(Connection connection) {
		this.connection = connection;
	}

	public void salvar(Reserva reserva) {
		try {
			String sql = "INSERT INTO Reservas (DataEntrada, DataSaida, Valor, FormaPagamento) values (?, ?, ?, ?)";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setDate(1, reserva.getDataEntrada());
				preparedStatement.setDate(2, reserva.getDataSaida());
				preparedStatement.setFloat(3, reserva.getValor());
				preparedStatement.setString(4, reserva.getFormaPagamento());

				preparedStatement.execute();

				try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
					while (resultSet.next()) {
						reserva.setId(resultSet.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Reserva> listar() {
		try {
			List<Reserva> reservas = new ArrayList<Reserva>();
			String sql = "SELECT * FROM Reservas";

			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.execute();

				transformarResultSetEmReserva(reservas, preparedStatement);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Reserva buscar(Integer id) {
		Reserva reserva;
		String sql = "SELECT * FROM Reservas WHERE Id = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			preparedStatement.execute();

			ResultSet resultSet = preparedStatement.getResultSet();
			reserva = new Reserva(resultSet.getInt(1), resultSet.getDate(2), resultSet.getDate(3),
					resultSet.getFloat(4), resultSet.getString(5));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return reserva;
	}

	public void deletar(Integer id) {
		String sql = "DELETE ? FROM Reservas WHERE Id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void alterar(Date dataEntrada, Date dataSaida, Float valor, String formaPagamento, Integer id) {
		String sql = "UPDATE Reservas RES SET RES.DataEntrada = ?, RES.DataSaida = ?, RES.Valor = ?, RES.FormaPagamento = ? WHERE Id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setDate(1, dataEntrada);
			preparedStatement.setDate(2, dataSaida);
			preparedStatement.setFloat(3, valor);
			preparedStatement.setString(4, formaPagamento);
			preparedStatement.setInt(5, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void transformarResultSetEmReserva(List<Reserva> reservas, PreparedStatement preparedStatement)
			throws SQLException {
		try (ResultSet resultSet = preparedStatement.getResultSet()) {
			while (resultSet.next()) {
				Reserva reserva = new Reserva(resultSet.getInt(1), resultSet.getDate(2), resultSet.getDate(3),
						resultSet.getFloat(4), resultSet.getString(5));

				reservas.add(reserva);
			}
		}
	}

}
