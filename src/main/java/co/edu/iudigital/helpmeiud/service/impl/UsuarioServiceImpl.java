package co.edu.iudigital.helpmeiud.service.impl;

import co.edu.iudigital.helpmeiud.dto.request.UsuarioDTORequest;
import co.edu.iudigital.helpmeiud.dto.respose.UsuarioDTO;
import co.edu.iudigital.helpmeiud.model.Role;
import co.edu.iudigital.helpmeiud.model.Usuario;
import co.edu.iudigital.helpmeiud.repository.IUsuarioRepository;
import co.edu.iudigital.helpmeiud.service.iface.IUsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {

    //@Autowired
    //private BCryptPasswordEncoder passwordEncoder;

    @Autowired //inyeccion de dependencias po atributo
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioDTO> consultarTodos() {
        return null;
    }

    @Override
    public UsuarioDTO consultarPorId(Long id) {
        return null;
    }

    @Override
    public UsuarioDTO consultarPorUsername(String username) {
        return null;
    }


    @Override
    public UsuarioDTO guardar(UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario;
        Role role = new Role();
        role.setId(10L);
        usuario = usuarioRepository.findByUsername(usuarioDTORequest.getUsername());
        if(usuario != null ){
            return null;
        }
        //String encoded = passwordEncoder.encode(usuarioDTORequest.getPassword());
        // log.info("password {}", encoded);
        usuario = new Usuario();
        // convertir usuarioDTORequest a usuario
        //usuario.setPassword(encoded);

        //convertir usuarioDtoRequest a usuario
        usuario.setNombre(usuarioDTORequest.getNombre());
        usuario.setUsername(usuarioDTORequest.getUsername());
        usuario.setApellido(usuarioDTORequest.getApellido());
        usuario.setPassword(usuarioDTORequest.getPassword());
        usuario.setImage(usuarioDTORequest.getImage());
        usuario.setEnabled(true);
        usuario.setRedSocial(false);
        usuario.setRoles(Collections.singletonList(role));
        usuario = usuarioRepository.save(usuario);

        return UsuarioDTO.builder()
                .username(usuario.getUsername())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .enabled(usuario.getEnabled())
                .fechaNacimiento(usuario.getFechaNacimiento())
                .redSocial(usuario.getRedSocial())
                .image(usuario.getImage())
                .roleId(usuario.getRoles().get(0).getId())

                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)// por ser consulta, readOnly
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if(usuario == null) {
            log.error("Error de login, no existe usuario: "+ usuario);
            throw new UsernameNotFoundException("Error de login, no existe usuario: "+ username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role: usuario.getRoles()) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getNombre());
            log.info("Rol {}", authority.getAuthority());
            authorities.add(authority);
        }
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,authorities);
    }

}
