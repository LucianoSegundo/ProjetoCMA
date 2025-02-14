package com.ifpe.projetoCMA.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifpe.projetoCMA.controller.dto.CadastroRequest;
import com.ifpe.projetoCMA.controller.dto.CadastroResponse;
import com.ifpe.projetoCMA.controller.dto.LoginRequest;
import com.ifpe.projetoCMA.controller.dto.LoginResponse;
import com.ifpe.projetoCMA.entity.Papel;
import com.ifpe.projetoCMA.entity.Usuario;
import com.ifpe.projetoCMA.exception.AcessoNegadoException;
import com.ifpe.projetoCMA.exception.CadastroNedadoException;
import com.ifpe.projetoCMA.exception.EntidadeNaoEncontradaException;
import com.ifpe.projetoCMA.repository.PapelRepository;
import com.ifpe.projetoCMA.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private UsuarioRepository userRepo;
	private PapelRepository papelRepo;
	private BCryptPasswordEncoder codSenha; // codifica a senha;
	private JwtEncoder jwtCodificador; // quem gera o token

	

	public UsuarioService(UsuarioRepository userRepo, PapelRepository papelRepo, BCryptPasswordEncoder codificadorSenha, JwtEncoder codificador) {
		this.userRepo = userRepo;
		this.papelRepo = papelRepo;
		this.codSenha = codificadorSenha;
		this.jwtCodificador = codificador;
	}
	
	@Transactional()
	public CadastroResponse cadastroUsuario(CadastroRequest cadRequest) {
		if (VerificarCampos.verificarTemCamposNulos(cadRequest))
			throw new CadastroNedadoException("Nenhum campo das informações do usuário deve ser nulo");

		if (cadRequest.nome().isBlank() || cadRequest.email().isBlank() || cadRequest.senha().isBlank() || cadRequest.usuario().isBlank())
			throw new CadastroNedadoException("Nenhum campo das informações do usuário pode estarem branco");

		if (userRepo.existsByNome(cadRequest.usuario()))
			throw new CadastroNedadoException("Usuario já cadastrado");
		
		if (userRepo.existsByEmail(cadRequest.email()))
			throw new CadastroNedadoException("Email já em uso.");

		Usuario user = new Usuario(cadRequest);

		String senhaCodificada = codSenha.encode(cadRequest.senha());

		Papel papel = papelRepo.findByAutoridade("aluno").orElseThrow(()-> new CadastroNedadoException("Alve um problema interno") );
	
		user.addPapel(papel);
		user.setSenha(senhaCodificada);
		
		Usuario usuarioSalvo = userRepo.save(user);
		

		CadastroResponse response = new CadastroResponse(usuarioSalvo.getUsuario(), "Usuario foi salvo com sucesso");
		return response;

	}
	
	@Transactional(readOnly = true)
	public LoginResponse Login(LoginRequest loginReques) {
		if (VerificarCampos.verificarTemCamposNulos(loginReques))
			throw new AcessoNegadoException("Nenhum campo da requisição de login deve ser nulo");

		Usuario consulta = userRepo.findByNome(loginReques.usuario())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Usuario não foi encontrado"));
		;

		if (codSenha.matches(loginReques.senha(), consulta.getSenha()))
			return gerarToken(consulta);
		else {
			throw new AcessoNegadoException("Não foi possivelfazer login.");
		}

	}
	@Transactional(readOnly = true)
	public List<CadastroResponse> ListarUsuarios( ) {
		
		return userRepo.findAll().stream().map(x -> new CadastroResponse(x.getUsuario(), "usuario")) .collect(Collectors.toList());
	}

	private LoginResponse gerarToken(Usuario usuario) {

		Instant now = Instant.now();
		Long expiresIn = 300L;
		var scopes = usuario.getPapeis().toString();

		var claims = JwtClaimsSet.builder().issuer("ProjetoCMA").subject(usuario.getId().toString())
				.expiresAt(now.plusSeconds(expiresIn)).claim("scape", scopes).build();
		var jwt = jwtCodificador.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		return new LoginResponse(jwt, expiresIn);

	}
	
}
