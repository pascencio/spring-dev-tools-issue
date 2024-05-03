package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/demo")
@RestController()
public class DemoController {

    private final DemoRepository demoRepository;

    public DemoController(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }

    @GetMapping("/greetings")
    public ResponseEntity<List<DemoEntity>> greetings() {
        List<DemoEntity> demoEntities = demoRepository.findAll();
        log.info("Greetings: [demoEntities='{}']", demoEntities);
        return ResponseEntity.ok(demoEntities);
    }

}
