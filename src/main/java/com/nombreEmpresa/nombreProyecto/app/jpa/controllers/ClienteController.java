package com.nombreEmpresa.nombreProyecto.app.jpa.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.nombreEmpresa.nombreProyecto.app.jpa.models.entitys.ClienteEntity;
import com.nombreEmpresa.nombreProyecto.app.jpa.models.services.IClienteService;
import com.nombreEmpresa.nombreProyecto.app.jpa.models.services.IUploadFileService;
import com.nombreEmpresa.nombreProyecto.app.jpa.util.paginator.PageRender;

@Controller("clienteController")
@RequestMapping("/app")
@SessionAttributes("cliente")
public class ClienteController {
	private static final Log LOGGER = LogFactory.getLog(ClienteController.class);
	@Value("${VIEW_LISTA}")
	private String VIEW_LISTA;
	@Value("${VIEW_FORM}")
	private String VIEW_FORM;
	@Value("${VIEW_INDEX}")
	private String VIEW_INDEX;
	@Value("${VIEW_VER}")
	private String VIEW_VER;
	
	@Autowired
	@Qualifier("clienteServiceImpl")
	private IClienteService clienteService;
	@Autowired
	@Qualifier("uploadServiceImpl")
	private IUploadFileService fileService;
	
	/**
	 * INDEX
	 */
	@GetMapping("/index")
	public String index(Model m) {
		m.addAttribute("titulo", "Index.WEB con JPA");
		LOGGER.info("METHOD...ClienteController.index(). " +
                "PARAM OUT : "+VIEW_INDEX);
	return VIEW_INDEX;
	}
	/**
	 * VER
	 */
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable (value = "id") Long id ,Model m,RedirectAttributes flash) {
		ClienteEntity cliente=clienteService.findOne(id);
		if(cliente==null) {
			
			flash.addAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/app/listar";
		}
		/**Revisamos cliente*/
		LOGGER.info("METHOD...ClienteController.ver(). " +
                "CLIENTE : Nombre:["+cliente.getNombre()+"]"+
                " Apellido:["+cliente.getApellido()+"]"+
                " ID:["+cliente.getId()+"]"+
                " MAIL:["+cliente.getEmail()+"]"+
                " FECHA:["+cliente.getFechaNacimiento().toString()+"]"+
                " CREADO:["+cliente.getCreateAt()+"]"+
                " FOTO:["+cliente.getFoto()+"]");
		LOGGER.info("METHOD...ClienteController.ver(). " +
                "FACTURAS:["+cliente.getFacturas().size()+"]");/**/
		m.addAttribute("cliente", cliente);
		m.addAttribute("titulo", "Detalles cliente: "+cliente.getNombre());
		
		
		LOGGER.info("METHOD...ClienteController.ver(). " +
                "PARAM OUT : "+VIEW_VER);
		
	return VIEW_VER;
	}
	/**
	 * VER FOTO
	 */
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable ("filename") String filename){
		Resource resource=null;
		try {
			resource=fileService.cargarImagen(filename);
			LOGGER.info("METHOD...ClienteController.verFoto(). " +
	                "PARAM OUT : "+resource.getFilename());
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().
				header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+resource.getFilename()+"\"").
				body(resource);
		
		
	}
	/**Vista para ver el listado de elementos*/
	@GetMapping("/listar")
	public String listar(@RequestParam(value = "page", defaultValue = "0") int page, Model m) {
		
		/**Objeto Pageable (numero de pagina, numero de elementos de la lista)*/
		Pageable pageable=PageRequest.of(page,5);
		
		/**Listado de ClienteEntity a partir de la consulta a la BD NO convertido a ClienteModel*/
		Page<ClienteEntity> clientes=clienteService.findAll(pageable);
		/*for(int i=0;i<clientes.toList().size();i++) {
			LOGGER.info("METHOD...ClienteController.listar(). " +
	                "PARAM OUT: "+ clientes.toList().get(i).getNombre()+
	                " Primero "+clientes.stream().findFirst().get().getNombre());
		}*/
		
		/**
		 * Clase PageRender necesita ruta, Page<T>*/
		PageRender<ClienteEntity> pageRender=new PageRender("/app/listar",clientes);
		m.addAttribute("page", pageRender);
		m.addAttribute("titulo", "Listado.WEB con JPA");
		
		/**CON PAGINADOR
		 * clienteService.findAll(pageable)=List<ClienteModel>
		 * */
		m.addAttribute("clientes",clientes);
		
		LOGGER.info("METHOD...ClienteController.listar(). " +
                "PARAM OUT: "+ VIEW_LISTA);
		
		return VIEW_LISTA;
	}
	
	/**CREAR
	 * Vista fomulario para introducir elmentos en la base de datos*/
	
	@GetMapping("/form")
	public String crear(Model model) {//Podemos poner un Model model----Map<String, Object> model
		/**
		 * Recogemos los datos del formulario con la clase ClienteModel
		 * y lo convertimos en este mismo metodo a la clase ClienteEntity.
		 * 
		 * 1.Asi no se manda los datos directamente del formulario a la tabla de
		 * la Base de Datos
		 * 2.Asi podemos validar errores en la clase ClienteModel y no en la clase ClienteEntity
		 * 
		 * Id de cliente es null, ya que el formulario no tiene campo para recogerlo
		 * */
		ClienteEntity cliente=new ClienteEntity();
		/*model.put("titulo", "Formulario.WEB con JPA");
		model.put("cliente", cliente);*/
		model.addAttribute("titulo", "Formulario.WEB con JPA");
		model.addAttribute("cliente", cliente);
		
		LOGGER.info("METHOD...ClienteController.crear(). " +
                "PARAM OUT: "+VIEW_FORM);
		return VIEW_FORM;//
	}
	/**GUARDAR
	 * Este metodo se ejecuta cuando pulsamos el boton.
	 * Vista fomulario para introducir elmentos en la base de datos.
	 * Aqui se produce la validacion cuando pulsamos el boton
	 *  @ModelAttribute(value="cliente") debe ser el mismo nombre que se pasa
	 *  a la vista en el GetMapping
	 * */
	@PostMapping("/form")
	public String guardar(@Valid @ModelAttribute(value="cliente") ClienteEntity  cliente,
			BindingResult result,Model m,RedirectAttributes flash, SessionStatus status,
			@RequestParam("file") MultipartFile foto) {
		
		/**Hay dos campos en cliente que son null: id y createAt ya que
		 * en el formulario no se recoje*/						
		/**Comprobamos que hay errores y si es asi devolvemos la vista form_ que es la que
         * estamos antes de pasar a la vista resultado2*/
        if(result.hasErrors()){
        	m.addAttribute("titulo","Formulario(errores).WEB con JPA");
        	String ruta_archivo=foto.getContentType();
        	LOGGER.info("METHOD...ClienteController.guardar().hasErrors() " +
	                "PARAM OUT: Errores:"+result.hasErrors()+
	                " TARGET "+result.getTarget().toString()+
	                " TARGET "+result.getObjectName());
        	
        	
            return VIEW_FORM;
        }	
        
        if(!foto.isEmpty()) {
        	if(cliente!=null &&
        			cliente.getId()!=null &&
        			cliente.getId()>0 &&
        			cliente.getFoto()!=null &&
        			cliente.getFoto().length()>0) {
        		//Si la foto ya existe se borra la foto
        		fileService.borrar(cliente.getFoto());
        		LOGGER.info("METHOD...ClienteController.guardar() " +
    	                "PARAM cliente!=null: "+cliente.getFoto().toString());
        	}
        	String unicoFileName=null;
			try {
				unicoFileName = fileService.copiar(foto);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			flash.addFlashAttribute("info","Has subido correctamente '"+unicoFileName+"'");
			//Establece un nombre foto para el ClienteModel
        	cliente.setFoto(unicoFileName);
        	LOGGER.info("METHOD...ClienteController.guardar() " +
	                "PARAM cliente.setFoto: "+cliente.getFoto().toString());
        }
        String flashMensaje=(cliente.getId()!=null)?"Cliente editado con exito":"Cliente creado con exito";
		
		/**
		 * Usamos el servicio que implementa un DAO con la clase ClienteModel
		 * para guardar los datos procedentes del formulario, ya que dentro del save()
		 * se realiza la conversion de ClienteModel a ClienteEntity
		 * */
		clienteService.save(cliente);
		//Cerramos la sesion
		status.setComplete();
		//Creamos un mensaje flash
		flash.addFlashAttribute("success", flashMensaje);
		LOGGER.info("METHOD...ClienteController.guardar(). " +
                "PARAM OUT: "+VIEW_LISTA);
		return "redirect:/app/listar";
	}
	/**ACTUALIZAR
	 * Vista fomulario para editar elmentos en la base de datos
	 **/
	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model,RedirectAttributes flash) {//Podemos poner un Model model
		/**
		 * Id procede de la bd (almacenado en el campo hidden del formulario)
		 * Pero cliente.getId() es null antes de clienteService.findOne(id).
		 * */
		ClienteEntity cliente=null;
		
		if(id>0) {
			LOGGER.info("METHOD...ClienteController.editar(). " +
	                "PARAM OUT ANTES: "+id);
			cliente=clienteService.findOne(id);
			if(cliente==null) {
				//Creamos un mensaje flash
				flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
				return "redirect:/app/listar";
			}
		}else {
			//Creamos un mensaje flash
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero");
			return "redirect:/app/listar";
		}
		
		/**
		 * ClienteModel tiene ya los valores de ID, ya que era una conversion de ClienteEntity desde
		 * clienteService.findOne(id)
		 * */
		model.addAttribute("titulo", "Editar cliente.WEB con JPA");
		model.addAttribute("cliente", cliente);
		LOGGER.info("METHOD...ClienteController.editar(). " +
                "PARAM OUT: "+VIEW_FORM);
		
		return VIEW_FORM;//
	}
	/**ELIMINAR
	 * Vista fomulario para eliminar elmentos en la base de datos*/
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id,RedirectAttributes flash) {//Podemos poner un Model model
				
		if(id>0) {
			//Buscamos al ClienteModel con el id
			ClienteEntity cliente=clienteService.findOne(id);
			
			if(!cliente.getFoto().isEmpty()) {
				if(fileService.borrar(cliente.getFoto())) {
					flash.addAttribute("info","Foto '"+cliente.getFoto()+" eliminada con exito");
				}
			}
				
			
			//Borramos el elemento de la db
			clienteService.delete(id);
			//Creamos un mensaje flash
			flash.addFlashAttribute("success", "Cliente eliminado con exito");
			
		}
		LOGGER.info("METHOD...ClienteController.eliminar(). " +
                "PARAM OUT: "+VIEW_FORM);
			return "redirect:/app/listar";
		
	}
	
	
}
