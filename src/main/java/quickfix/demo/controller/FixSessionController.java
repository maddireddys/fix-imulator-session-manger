package quickfix.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import quickfix.ConfigError;
import quickfix.demo.api.FixSession;
import quickfix.demo.api.Response;
import quickfix.demo.manager.FixSessionManager;

import java.util.Collection;

@RestController
public class FixSessionController {

  @Autowired
  private FixSessionManager sessionManager;

  @PostMapping("/session/start")
  @CrossOrigin(origins = "*", allowedHeaders = "*")
  public Response<FixSession> startSession(@RequestBody FixSession session) throws ConfigError {
    return new Response<>(200, "Started", sessionManager.startSession(session));
  }

  @PostMapping("/session/add")
  @CrossOrigin(origins = "*", allowedHeaders = "*")
  public Response<FixSession> addSession(@RequestBody FixSession session) {
    return new Response<>(200, "Started", sessionManager.addSession(session));
  }

  @PostMapping("/session/stop")
  @CrossOrigin(origins = "*", allowedHeaders = "*")
  public Response<FixSession> stopSession(@RequestBody FixSession session) throws ConfigError {
    return new Response<>(200, "Stopped", sessionManager.stopSession(session));
  }

  @GetMapping("/session/load")
  @CrossOrigin(origins = "*", allowedHeaders = "*")
  public Response<Collection<FixSession>> listSession() throws ConfigError {
    return new Response<>(200, "Started", sessionManager.listSession());
  }
}
