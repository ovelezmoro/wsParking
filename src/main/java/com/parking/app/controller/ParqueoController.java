package com.parking.app.controller;

import com.parking.app.dto.TParqueoDTO;
import com.parking.app.entity.TParqueo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Created by LENOVO on 28/03/2019.
 */
@Slf4j
@Api
@RestController
@RequestMapping("plaqueo/")
public class ParqueoController {

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "save", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "save", nickname = "Guardar Parqueo", response = TParqueoDTO.class)
    @ResponseBody
    public TParqueo save(@RequestBody(required = false) TParqueoDTO parqueo) {

        return null;

    }

}
