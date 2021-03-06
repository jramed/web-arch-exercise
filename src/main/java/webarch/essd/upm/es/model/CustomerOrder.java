package webarch.essd.upm.es.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
//import javax.validation.constraints.NotEmpty;


@Entity
public class CustomerOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
//	@NotEmpty
	private String name;

//	@NotEmpty
	@ManyToMany(mappedBy="orders")
	private List<Product> products = new ArrayList<Product>();

	protected CustomerOrder() {
	}

	public List<Product> getProducts() {
		return products;
	}
	
	public List<String> getProducstNames() {
		List<Product> products = getProducts();
		List<String> productsNames = new ArrayList<>();
		for (Product product: products) {
			productsNames.add(product.getName());
		}
		return productsNames;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public CustomerOrder(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CustomerOrder [id=" + id + ", name=" + name + ", products= " + products + "]";
	}

}
