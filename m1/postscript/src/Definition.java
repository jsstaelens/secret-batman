/**
 * Class Definition représentant un lien entre un mot et une valeur
 * 
 * @author Groupe 5.2
 * 
 */
public class Definition {
	// Variable d'instace de la classe definition
	private String name;
	private double value;

	// Constructeur de la classe definition
	public Definition(String name, double value) {
		this.name = name;
		this.value = value;
	}

	// GETTEUR
	public String getName() {
		return name;
	}

	public double getValue() {
		return value;
	}

	// SETTER
	public void setName(String name) {
		this.name = name;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
