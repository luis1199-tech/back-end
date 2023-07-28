package co.edu.iudigital.helpmeiud.controller;

import co.edu.iudigital.helpmeiud.dto.request.DelitoDTORequest;
import co.edu.iudigital.helpmeiud.dto.respose.DelitoDTO;
import co.edu.iudigital.helpmeiud.excepcions.RestException;
import co.edu.iudigital.helpmeiud.service.iface.IDelitoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/delitos")
@Api(value = "/delitos", tags = {"Delitos"})
@SwaggerDefinition(tags = {
        @Tag(name = "Delitos", description = "Gestion API Delitos")
})
public class DelitoController {

    @Autowired
    IDelitoService delitoService;

    @ApiOperation(value = "Obtiene todos delitos",
            //response = List.class
            responseContainer = "List",
            produces = "application/json",
            httpMethod = "GET")
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<DelitoDTO>> index(){
        return ResponseEntity
                .ok().body(delitoService.consultarTodos());
    }

    @ApiOperation(value = "Guardar un Delito",
            response = DelitoDTO.class,
            responseContainer = "DelitoDTO",
            produces = "application/json",
            httpMethod = "POST")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<DelitoDTO> create(@RequestBody @Valid DelitoDTORequest delitoDTORequest) throws RestException {
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(delitoService.guardarDelito(delitoDTORequest));
    }
}
