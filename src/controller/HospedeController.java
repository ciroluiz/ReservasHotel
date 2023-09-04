package controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import dao.HospedeDAO;
import factory.ConnectionFactory;
import model.Hospede;

public class HospedeController {
	private HospedeDAO hospedeDAO;

	public HospedeController(HospedeDAO hospedeDAO) {
		Connection connection = new ConnectionFactory().recuperarConexao();
		this.hospedeDAO = new HospedeDAO(connection);
	}

	public void salvar(Hospede hospede) {
		this.hospedeDAO.salvar(hospede);
	}

	public List<Hospede> listar() {
		return this.hospedeDAO.listar();
	}

	public Hospede buscar(String sobrenome) {
		return this.hospedeDAO.buscar(sobrenome);
	}

	public void deletar(Integer id) {
		this.hospedeDAO.deletar(id);
	}

	public void alterar(String nome, String sobrenome, Date dataNascimento, String nacionalidade, String telefone,
			Integer id) {
		this.hospedeDAO.alterar(nome, sobrenome, dataNascimento, nacionalidade, telefone, id);
	}
}
