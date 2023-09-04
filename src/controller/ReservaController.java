package controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import dao.ReservaDAO;
import factory.ConnectionFactory;
import model.Reserva;

public class ReservaController {
	private ReservaDAO reservaDAO;

	public ReservaController(ReservaDAO reservaDAO) {
		Connection connection = new ConnectionFactory().recuperarConexao();
		this.reservaDAO = new ReservaDAO(connection);
	}

	public void salvar(Reserva reserva) {
		this.reservaDAO.salvar(reserva);
	}

	public List<Reserva> listar() {
		return this.reservaDAO.listar();
	}

	public Reserva buscar(Integer id) {
		return this.reservaDAO.buscar(id);
	}

	public void deletar(Integer id) {
		this.reservaDAO.deletar(id);
	}

	public void alterar(Date dataEntrada, Date dataSaida, Float valor, String formaPagamento, Integer id) {
		this.reservaDAO.alterar(dataEntrada, dataSaida, valor, formaPagamento, id);
	}
}
