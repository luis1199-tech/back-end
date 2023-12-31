package co.edu.iudigital.helpmeiud.service.impl;

import co.edu.iudigital.helpmeiud.dto.CasoDTO;
import co.edu.iudigital.helpmeiud.excepcions.BadRequestException;
import co.edu.iudigital.helpmeiud.excepcions.ErrorDto;
import co.edu.iudigital.helpmeiud.excepcions.RestException;
import co.edu.iudigital.helpmeiud.model.Caso;
import co.edu.iudigital.helpmeiud.model.Delito;
import co.edu.iudigital.helpmeiud.model.Usuario;
import co.edu.iudigital.helpmeiud.repository.ICasoRepository;
import co.edu.iudigital.helpmeiud.repository.IDelitoRepository;
import co.edu.iudigital.helpmeiud.repository.IUsuarioRepository;
import co.edu.iudigital.helpmeiud.service.iface.ICasoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CasoServiceImpl
        implements ICasoService {

    @Autowired
    private ICasoRepository casoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IDelitoRepository delitoRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CasoDTO> consultarTodos() {
        log.info("consultando todos los casos{}");
        List<Caso> casos = casoRepository.findAll();
        return casos.stream().map(caso ->
            CasoDTO.builder()
                    .id(caso.getId())
                    .descripcion(caso.getDescripcion())
                    .altitud(caso.getAltitud())
                    .latitud(caso.getLatitud())
                    .longitud(caso.getLongitud())
                    .esVisible(caso.getEsVisible())
                    .fechaHora(caso.getFechaHora())
                    .rmiUrl(caso.getRmiUrl())
                    .urlMap(caso.getUrlMap())
                    .usuarioId(caso.getUsuario().getId())
                    .delitoId(caso.getDelito().getId())
                    .build()
        ).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Caso crear(CasoDTO casoDTO) throws RestException {// TODO: lanzar excepcion
        Optional<Usuario> usuario = usuarioRepository
                .findById(casoDTO.getUsuarioId());
        Optional<Delito> delito = delitoRepository
                .findById(casoDTO.getDelitoId());
        if(!usuario.isPresent() || !delito.isPresent()){
            log.error("No existe usuario {}", casoDTO.getUsuarioId());
            throw new BadRequestException(
                    ErrorDto.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message("No existe usuario o delito")
                            .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .date(LocalDateTime.now())
                            .build()
            );
        }
        Caso caso = new Caso();
        caso.setFechaHora(casoDTO.getFechaHora());
        caso.setLatitud(casoDTO.getLatitud());
        caso.setLongitud(casoDTO.getLongitud());
        caso.setAltitud(casoDTO.getAltitud());
        caso.setDescripcion(casoDTO.getDescripcion());
        caso.setEsVisible(true);
        caso.setUrlMap(casoDTO.getUrlMap());
        caso.setRmiUrl(casoDTO.getRmiUrl());
        caso.setUsuario(usuario.get());
        caso.setDelito(delito.get());
        return casoRepository.save(caso);
    }

    @Transactional
    @Override
    public Boolean visible(Boolean visible, Long id) {
        return casoRepository.setVisible(visible, id);
    }

    @Transactional(readOnly = true)
    @Override
    public CasoDTO consultarPorId(Long id) {
        Optional<Caso> casoOptional = casoRepository.findById(id);
        if(casoOptional.isPresent()){
            Caso caso = casoOptional.get();
            return CasoDTO.builder()
                    .id(caso.getId())
                    .descripcion(caso.getDescripcion())
                    .altitud(caso.getAltitud())
                    .latitud(caso.getLatitud())
                    .longitud(caso.getLongitud())
                    .esVisible(caso.getEsVisible())
                    .fechaHora(caso.getFechaHora())
                    .rmiUrl(caso.getRmiUrl())
                    .urlMap(caso.getUrlMap())
                    .usuarioId(caso.getUsuario().getId())
                    .delitoId(caso.getDelito().getId())
                    .build();
        }
        log.warn("No existe usuario {}", id);
        return null; //TODO: controlar con excepciones
    }
}
