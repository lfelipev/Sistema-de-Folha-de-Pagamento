package P3SystemJava;

import P3SystemJava.SalariedEmployee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class SalariedEmployee extends Employee {
	public double fixedSalary;
	public boolean commissioned; //true = sim; false = nÃ£o
	public double commissionFactor; //porcentagem de comissÃ£o a cada venda
	public double totalCommission; //valor de comissÃµes acumulado
	
	Scanner in = new Scanner(System.in);

	public SalariedEmployee() {
		
	}
	
	/*
	public SalariedEmployee(String name, String address, String type, String paymentMethod,
			String unionName, double unionTax, double totalSalary, boolean union, int id, 
			double fixedSalary, boolean commissioned, double commissionFactor,
			double totalCommission) {
		
		this.name = name;
		this.address = address;
		this.type = type;
		this.paymentMethod = paymentMethod;
		this.unionName = unionName;
		this.unionTax = unionTax;
		this.totalSalary = totalSalary;
		this.union = union;
		this.ID = id;
		this.fixedSalary = fixedSalary;
		this.commissioned = commissioned;
		this.commissionFactor = commissionFactor;
		this.totalCommission = totalCommission;
	} */
	
	/**
	 * Getters e Setters
	 */
	public double getFixedSalary() {
		return fixedSalary;
	}
	
	public void setFixedSalary(double fixedSalary) {
		this.fixedSalary = fixedSalary;
	}
	
	public boolean getCommissioned() {
		return commissioned;
	}
	
	public void setCommissioned(boolean commissioned) {
		this.commissioned = commissioned;
	}
	
	public double getCommissionFactor() {
		return commissionFactor;
	}
	
	public void setCommissionFactor(double commissionFactor) {
		this.commissionFactor = commissionFactor;
	}
	
	public double getTotalCommission() {
		return totalCommission;
	}
	
	public void setTotalCommission(double totalCommission) {
		this.totalCommission = totalCommission;
	}
	
	/**
	 * Métodos
	 * @param stack1 
	 */
	public void employeeRegister(String type, ArrayList<Employee> employeeList) {
		
		int method = 0;
		
		Scanner sc = new Scanner(System.in);
		
		SalariedEmployee sEmployee = new SalariedEmployee();
		
		sEmployee.setType(type);
		
		System.out.println("===============================");
		System.out.println("---- CADASTRO DE EMPREGADO ----");
		System.out.println("===============================");
		
		System.out.println("NOME: ");
		sEmployee.setName(in.nextLine());
		
		System.out.println("ENDEREÇO: ");
		sEmployee.setAddress(in.nextLine());
		
		double salary;
		boolean ready = false;
		while(!ready) {
			try {
				System.out.println("SALÁRIO FIXO MENSAL: ");
				salary = Double.parseDouble(sc.next());
				sEmployee.setFixedSalary(salary);
				ready = true;
			} catch (NumberFormatException n) {
				System.out.println("FALHA!" + 
			" O Valor digitado não é um double." + n.getMessage());
			}
		}
		
		ready = false;
		while(!ready) {
			try {
				System.out.println("MÉTODO DE PAGAMENTO DO SALÁRIO: ");
				System.out.println("[1]Cheque pelos correios.");
				System.out.println("[2]Cheque em mãos.");
				System.out.println("[3]Depósito em conta banária.");
				method = Integer.parseInt(sc.next()); 
				ready = true;
			} catch (NumberFormatException n) {
				System.out.println("FALHA!" + 
			" O Valor digitado não é um inteiro." + n.getMessage());
			}
		}
		
		switch(method) {
			case 1:
				sEmployee.setPaymentMethod("Cheque pelos correios.");
			case 2:
				sEmployee.setPaymentMethod("Recebimento em mãos.");
			case 3:
				sEmployee.setPaymentMethod("Depósito em conta bancária.");
		}
		
		method = 0;
		ready = false;
		while(!ready) {
			try {
				System.out.println("SELECIONE: ");
				System.out.println("[1]Comissionado.");
				System.out.println("[2]Não comissionado.");;
				method = Integer.parseInt(sc.next()); 
				ready = true;
			} catch (NumberFormatException n) {
				System.out.println("FALHA!" + 
			" O Valor digitado não é um inteiro." + n.getMessage());
			}
		}
		
		switch(method) {
			case 1:
				sEmployee.setCommissioned(true); break;
			case 2:
				sEmployee.setCommissioned(false); break;
		}
		
		/*
		 *Se é comissionado, o pagamento será para daqui a duas sextas feiras
		 */
		if(sEmployee.commissioned) {
			Calendar today = Calendar.getInstance();
			int daysOfWeek = today.get(Calendar.DAY_OF_WEEK);
			int daysUntilNextFriday = Calendar.FRIDAY - daysOfWeek;
			
			Calendar nextFriday = (Calendar) today.clone();
			nextFriday.add(Calendar.DAY_OF_WEEK, daysUntilNextFriday);
			
			if(nextFriday.get(Calendar.DAY_OF_YEAR) % 2 == 0) {
				nextFriday.add(Calendar.DAY_OF_WEEK, 14);
			}
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			sEmployee.setPaymentDate(nextFriday);
			
			salary = 0;
			ready = false;
			while(!ready) {
				try {
					System.out.println("PORTENTAGEM DE COMISSÃO: ");
					salary = Double.parseDouble(sc.next());
					sEmployee.setCommissionFactor(salary);
					ready = true;
				} catch (NumberFormatException n) {
					System.out.println("FALHA!" + 
				" O Valor digitado não é um double." + n.getMessage());
				}
			}
			
			sEmployee.setDateFormat(dateFormat.format(nextFriday.getTime()));
		}
		//Se não, o pagamento será no dia primeiro do próximo mês
		else {
			//cria o formatador
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			//instancia a data de hoje
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.DATE, -1);
			while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
					cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				cal.add(Calendar.DATE, -1);
				
			}
			//adciona 1 mês à data atual
			//cal.set(Calendar.DATE, 	cal.getActualMaximum(Calendar.DATE));
			//seta cal para a classe
			sEmployee.setPaymentDate(cal);
			//seta a data formatada para a classe
			sEmployee.setDateFormat(dateFormat.format(cal.getTime()));
			//teste
		}
		
		method = 0;
		ready = false;
		while(!ready) {
			try {
				System.out.println("SELECIONE: ");
				System.out.println("[1]Sindicalizado.");
				System.out.println("[2]Não sidicalizado.");
				method = Integer.parseInt(sc.next()); 
				ready = true;
			} catch (NumberFormatException n) {
				System.out.println("FALHA!" + 
			" O Valor digitado não é um inteiro." + n.getMessage());
			}
		}
		
		switch(method) {
			case 1:
				sEmployee.setUnion(true);
				System.out.println("NOME DO SINDICATO: ");
				sEmployee.setUnionName(in.nextLine());
				
				salary = 0;
				ready = false;
				while(!ready) {
					try {
						System.out.println("TAXA SINDICAL: ");
						salary = Double.parseDouble(sc.next());
						sEmployee.setUnionTax(salary);
						ready = true;
					} catch (NumberFormatException n) {
						System.out.println("FALHA!" + 
					" O Valor digitado não é um double." + n.getMessage());
					}
				}
				
				break;
			case 2:
				sEmployee.setUnion(false);
				break;
		}
		
		System.out.println("Empregado Cadastrado com Sucesso!");
		System.out.printf("Nome: %s.\n", sEmployee.name);
		System.out.printf("Endereço: %s.\n", sEmployee.address);
		System.out.printf("Tipo: %s.\n", sEmployee.type);
		System.out.printf("Salário Fixo: %f.\n", sEmployee.fixedSalary);
		System.out.println("Dia do Pagamento: "+ sEmployee.getDateFormat());
		
		employeeList.add(sEmployee);
	}
	
	public void saleReport(ArrayList<Employee> employeeList) {
		Scanner sc = new Scanner(System.in);
		String name;
		int choose;
		
		System.out.println("=======================================");
		System.out.println("---- LANÇAR UM RESULTADO DE VENDA -----");
		System.out.println("=======================================");
		System.out.println("Digite seu nome: ");
		name = sc.nextLine();
		
		for(int i=0; i<employeeList.size(); ++i) {
			SalariedEmployee e  = (SalariedEmployee) employeeList.get(i);
			
			if(e.getName().equals(name)) {
				System.out.println("A data recebida da venda é: ");
				System.out.println(getDateTime());
				
				double value = 0;
				boolean ready = false;
				while(!ready) {
					try {
						System.out.println("Informe o valor da venda: ");
						value = Double.parseDouble(sc.next());
						e.setTotalCommission(e.getTotalCommission() + (value * e.getCommissionFactor()));;
						ready = true;
					} catch (NumberFormatException n) {
						System.out.println("FALHA!" + 
					" O Valor digitado não é um double." + n.getMessage());
					}
				}
			}
		}
	}
	
	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
}	
