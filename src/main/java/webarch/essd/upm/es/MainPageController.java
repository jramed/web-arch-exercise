package webarch.essd.upm.es;

import java.util.List;
import java.util.Optional;

//import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import webarch.essd.upm.es.model.CustomerOrder;
import webarch.essd.upm.es.model.Product;

@Controller
public class MainPageController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CustomerOrderRepository customerOrderRepository;
	
/*	@PostConstruct
	public void init() {

		CustomerOrder order1 = new CustomerOrder("Selección");
		CustomerOrder order2 = new CustomerOrder("FC Barcelona");
		CustomerOrder order3 = new CustomerOrder("Atlético de Madrid");
		
		customerOrderRepository.save(order1);
		customerOrderRepository.save(order2);
		customerOrderRepository.save(order3);
		
		Product p1 = new Product("product1");
		Product p2 = new Product("product2");
		
		p1.getOrders().add(order1);
		p1.getOrders().add(order2);
		
		p2.getOrders().add(order1);
		p2.getOrders().add(order3);
		
		productRepository.save(p1);
		productRepository.save(p2);
}*/
	
	@GetMapping("/wa-main-page")
	public String customerOrders(Model model) {
		generateMainPageData(model);
		return "wa-main-page";
	}

	private void generateMainPageData(Model model) {
		List<CustomerOrder> customerOrders = customerOrderRepository.findAll();
		boolean showEmptyOrdersMsg = false ;
		boolean showOrders = false;
		if(0 == customerOrders.size()) {
			showEmptyOrdersMsg = true;
		}
		else {
			showOrders = true;
		}

		model.addAttribute("showEmptyOrdersMsg", showEmptyOrdersMsg);
		model.addAttribute("showOrders", showOrders);
		model.addAttribute("customerOrders", customerOrders);
	}
	
	//web page with jscript instead for mustache template?
	@GetMapping("/wa-place-new-order")
	public String placeNewOrder(Model model) {
		
		return "wa-place-new-order";
	}
	
	@GetMapping("/wa-store-order")
	public String storeNewOrder(Model model, @RequestParam String name, 
			@RequestParam(value="productNames[]") String[] productNames) {
		
		CustomerOrder customerOrder = new CustomerOrder(name);
		CustomerOrder customerOrder1 = customerOrderRepository.save(customerOrder);
		
		for (String productName : productNames) {
			Product p1 = new Product(productName);
			p1.getOrders().add(customerOrder1);
			productRepository.save(p1);
		}
		
		System.out.println("The order content is: " + customerOrder1.toString());
		generateMainPageData(model);		
		return "wa-main-page";
	}
	
	@GetMapping("/wa-detail-order/{id:\\d+}")
	public String detailOrder(Model model, @PathVariable long id) {
		Optional<CustomerOrder> customerOrder = customerOrderRepository.findById(id);
		if (customerOrder.isPresent()) {
			System.out.println("The order content read form DB is: "+ customerOrder.toString());
			model.addAttribute("name", customerOrder.get().getName());
			List<String> products = customerOrder.get().getProducstNames();
			System.out.println("The list of products is: "+ products.toString());
			model.addAttribute("products", products);
			model.addAttribute("id", id);
			return "wa-shop-basket";
		}
		
		return "404.html";
	}
	
	
}
