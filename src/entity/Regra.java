package entity;

public class Regra {
	int devolver;
	int bloquear;
	public int getDevolver() {
		return devolver;
	}
	public void setDevolver(int devolver) {
		this.devolver = devolver;
	}
	public int getBloquear() {
		return bloquear;
	}
	public void setBloquear(int bloquear) {
		this.bloquear = bloquear;
	}
	@Override
	public String toString() {
		return "Regra [devolver=" + devolver + ", bloquear=" + bloquear + "]";
	}
	
	
}
