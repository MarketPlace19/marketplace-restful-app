package br.com.marketplace.enums;

public enum EnumProfile {

	SYSTEM_ADMINISTRATOR("System Administrator", "The prolife will manage whole system."),
	CONSUMER("Consumer", "The profile will make shopping and receive offs from app."),
	PROVIDER("Provider", "The profile will register product, manage storage, etc.");
	
	private String name;
	private String description;
	
	EnumProfile(String name, String description) {
		this.setName(name);
		this.setDescription(description);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
