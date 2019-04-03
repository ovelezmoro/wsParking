package com.parking.app.controller;

import com.parking.app.dao.IParqueoDAO;
import com.parking.app.dto.TParqueoDTO;
import com.parking.app.entity.TParqueo;
import com.parking.app.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LENOVO on 28/03/2019.
 */
@Slf4j
@Api
@RestController
@RequestMapping("parqueo/")
public class ParqueoController {

    @Autowired
    IParqueoDAO iParqueoDAO;

    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "image", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public Map<String, Object> save(@RequestBody(required = true) Map<String, String> image) {

        Map<String, Object> map = new HashMap<>();
        map.put("message", "success");

        String photo = image.get("imagen").replace("data:image/png;base64,", "");
        byte[] decoded = Base64.decodeBase64(photo);

        String time = StrUtil.getString(new Date().getTime());

        try(BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("images/" + time + ".png"))){
            bufferedOutputStream.write(decoded);
            map.put("image", time + ".png");
        }catch (IOException ex){
            log.error(ex.getMessage(), ex);
            map.put("message", "error");
        };

        return map;

    }


    @CrossOrigin(origins = {"http://localhost:8100", "file://", "*"})
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.POST}, value = "save", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public TParqueo save(@RequestBody(required = false) TParqueoDTO parqueo) {

        TParqueo tParqueo = new TParqueo(parqueo);

        iParqueoDAO.save(tParqueo);

        return tParqueo;

    }

}
