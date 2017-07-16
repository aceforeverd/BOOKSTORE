package org.sjtu.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import  com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import javax.persistence.criteria.CriteriaQuery;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
/**
 * Created by ace on 7/15/17.
 */
@Controller
@RequestMapping(path = "/files")
public class FileController {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<String> createOrUpdate(@RequestParam("file") MultipartFile file,
                                             @RequestParam(defaultValue = "0") Integer userId,
                                             @RequestParam(defaultValue = "0") Integer bookId) {
        String name = file.getOriginalFilename();
        DBObject metadata = new BasicDBObject();
        if (userId != 0) {
            metadata.put("userId", userId);
        }
        if (bookId != 0) {
            metadata.put("bookId", bookId);
        }
        try {
            /*
            Optional<GridFSDBFile> existing = maybeLoadFile(name);
            if (existing.isPresent()) {
                gridFsTemplate.delete(getFilenameQuery(name));
            }
            */
            gridFsTemplate.store(file.getInputStream(), name, file.getContentType(), metadata).save();
            return new ResponseEntity<String>(
                    "{\"message\": \"saved\"}",
                    HttpStatus.OK
            );
        } catch (IOException e) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(path = {"/",""}, method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> get(@RequestParam(defaultValue = "0") Integer bookId,
                                  @RequestParam(defaultValue = "0") Integer userId) {
        List<GridFSDBFile> gridFSDBFiles ;
        if (bookId > 0 && userId == 0) {
            gridFSDBFiles = gridFsTemplate.find(new Query(Criteria.where("metadata.bookId").is(bookId)));
        } else if (bookId == 0 && userId > 0) {
            gridFSDBFiles = gridFsTemplate.find(new Query(Criteria.where("metadata.userId").is(userId)));
        } else {
            return new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        }

        GridFSDBFile file = gridFSDBFiles.get(0);
        ByteArrayOutputStream  byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            file.writeTo(byteArrayOutputStream);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, file.getContentType());
            return new HttpEntity<byte[]>(byteArrayOutputStream.toByteArray(), headers);
        } catch (IOException e) {
            return new ResponseEntity<byte[]>(HttpStatus.IM_USED);
        }

    }

    /*
    @RequestMapping(path = {"/{id}","/{id}/"})
    @ResponseBody
    public ResponseEntity<String> deleteFile(@PathVariable Integer id) {

    }
    */

}
