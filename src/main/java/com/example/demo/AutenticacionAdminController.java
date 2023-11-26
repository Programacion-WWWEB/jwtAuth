package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticacionAdminController {

        @CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"}, allowedHeaders = "*")
    @PostMapping( value = "/admin/autenticacion-usuario", produces = MediaType.APPLICATION_JSON_VALUE )
    public JWTToken autenticarAdmin( @RequestParam( required = true) String correo, @RequestParam( required = true     ) String contrasena ){
        System.out.println("----------1<<<<<<<>>>>>><-------");
        System.out.println(  correo + " --- " + contrasena);
        JWTProveedorToken jwtProveedorToken = new JWTProveedorToken();
        return  new JWTToken(jwtProveedorToken.generateAdminToken(correo, contrasena), JWTFiltroAutorizacion.PREFIX) ;
    }
    
}
