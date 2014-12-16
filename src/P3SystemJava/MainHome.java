package P3SystemJava;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

import P3SystemJava.Employee;
import P3SystemJava.SalariedEmployee;
import P3SystemJava.HourlyEmployee;

public class MainHome {

	public static void main(String[] args) throws NumberFormatException {
		Stack<ArrayList<Employee>> stack1 = new Stack<ArrayList<Employee>>();
		Stack<ArrayList<Employee>> stack2 = new Stack<ArrayList<Employee>>();
		Scanner sc = new Scanner(System.in);
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		ArrayList<Employee> oldList = new ArrayList<Employee>();
		ArrayList<Employee> redoList = new ArrayList<Employee>();
		SalariedEmployee sEmployee = new SalariedEmployee();
		HourlyEmployee hEmployee = new HourlyEmployee();
		
		int choose = 0;
		int id = 1;
		
		while (choose != 11)
		{
			boolean ready = false;
			while(!ready) {
				try {
					System.out.println("===================================");
					System.out.println("---------- MENU PRINCIPAL ---------");
					System.out.println("===================================");
					System.out.println("[1]Adição de um empregado.");
					System.out.println("[2]Remoção de um empregado.");
					System.out.println("[3]Alterar detalhes de um empregado.");
					System.out.println("[4]Lançar um resultado venda.");
					System.out.println("[5]Lançar um cartão de ponto.");
					System.out.println("[6]Lançar uma taxa de serviço.");
					System.out.println("[7]Rodar a folha de pagamento para hoje.");
					System.out.println("[8]Undo");
					System.out.println("[9]Redo");
					System.out.println("[10]Listar");
					choose = Integer.parseInt(sc.next()); 
					ready = true;
				} catch (NumberFormatException n) {
					System.out.println("FALHA!" + 
				" O Valor digitado não é um inteiro." + n.getMessage());
				}
			}
			
			switch (choose)
			{
			case 1:
				//oldList = clone(employeeList);
				chooseType(sEmployee, hEmployee, employeeList);
				stack1.push(clone(employeeList));
				break;
			case 2:
				System.out.println("=================================");
				System.out.println("----- REMOÇÃO DE EMPREGADO ------");
				System.out.println("=================================");
				//oldList = clone(employeeList);
				searchToRemove(employeeList);
				stack1.push(clone(employeeList));
				break;
			case 3:
				//oldList = clone(employeeList);
				changeEmployee(employeeList, stack1, hEmployee, sEmployee);
				stack1.push(clone(employeeList));
				break;
			case 4:
				//oldList = clone(employeeList);
				sEmployee.saleReport(employeeList);
				stack1.push(clone(employeeList));
				break;
			case 5:
				//oldList = clone(employeeList);
				hEmployee.pointSet(employeeList);
				stack1.push(clone(employeeList));
				break;
			case 6:
				//oldList = clone(employeeList);
				serviceTax(employeeList);
				stack1.push(clone(employeeList));
				break;
			case 7:
				//oldList = clone(employeeList);
				paymentSet(employeeList);
				stack1.push(clone(employeeList));
				break;
			case 8:
				stack1.pop();
				if(stack1.isEmpty()) {
					System.out.println("Undo Vazio!");
				}
				else {
					stack2.push(clone(employeeList));
					employeeList = clone(stack1.peek());
					System.out.println("Desfeito");
				}
				//redoList = clone(employeeList);
				//employeeList = clone(oldList);
				break;
			case 9:
				stack1.pop();
				if(stack1.isEmpty()) {
					System.out.println("Redo Vazio!");
				}
				else {
					employeeList = clone(stack2.pop());
					System.out.println("Refeito");
				}
				
				//employeeList = clone(redoList);
				break;
			case 10:
				list(employeeList);
				break;
			default:
				System.out.println("Opção inválida");
				break;
			}
			id++;
		}

	}
	
	/**
	 * Antes de cadastrar um funcion�rio, deve-se escolher o tipo
	 * @param sEmployee
	 * @param hEmployee
	 * @param employeeList
	 * @param stack1 
	 */
	public static void chooseType(SalariedEmployee sEmployee, HourlyEmployee hEmployee, 
			ArrayList<Employee> employeeList) {
		Scanner sc = new Scanner(System.in);
		int choose = 0;
		
		boolean ready = false;
		while(!ready) {
			try {
				System.out.println("Escolha o tipo de empregado a ser cadastrado");
				System.out.println("[1]Horista [2]Assalariado");
				choose = Integer.parseInt(sc.next()); 
				ready = true;
			} catch (NumberFormatException n) {
				System.out.println("FALHA!" + 
			" O Valor digitado não é um inteiro." + n.getMessage());
			}
		}
		
		switch(choose) {
			case 1:
				sc.nextLine();
				hEmployee.employeeRegister("Horista", employeeList);
				break;
			case 2:
				sc.nextLine();
				sEmployee.employeeRegister("Assalariado", employeeList);
				break;
		}
	}
	
	/**
	 * Fun��o para remover um funcion�rio da EmployeeList
	 * @param employeeList
	 * @param stack1 
	 */
	public static void searchToRemove(ArrayList<Employee> employeeList) {
		Scanner sc = new Scanner(System.in);
		String name;
		
		System.out.println("Digite o nome do empregado: ");
		name = sc.next();
		
		for(int i=0; i<employeeList.size(); ++i) {
			Employee e = employeeList.get(i);
			
			if(e.getName().equals(name)) {
				employeeList.remove(e);
				break;
			}
		}
	}
	
	/**
	 * Simples Check
	 * @param employeeList
	 */
	public static void list(ArrayList<Employee> employeeList) {
		for(Employee e : employeeList) {
			System.out.println(e.name);
			System.out.println(e.address);
			System.out.println(e.paymentMethod);
			System.out.println("-----");
		}
	}
	
	public static void serviceTax(ArrayList<Employee> employeeList) {
		Scanner sc = new Scanner(System.in);
		String name;
		
		System.out.println("=======================================");
		System.out.println("----- LANÇAR UMA TAXA DE SERVIÇO ------");
		System.out.println("=======================================");
		System.out.println("Digite seu nome: ");
		name = sc.nextLine();
		
		for(int i=0; i<employeeList.size(); ++i) {
			SalariedEmployee e  = (SalariedEmployee) employeeList.get(i);
			
			if(e.getName().equals(name)) {
				double value;
				
				boolean ready = false;
				while(!ready) {
					try {
						System.out.println("Digite o valor da taxa a ser cobrada: ");
						value = sc.nextDouble();
						sc.nextLine();
						
						e.totalSalary = e.totalSalary - value;
						System.out.println("A taxa foi deduzida do montante salarial atual.");
						ready = true;
					} catch (NumberFormatException n) {
						System.out.println("FALHA!" + 
					" O Valor digitado não é um double." + n.getMessage());
					}
				}
			}
		}
	}
	
	public static void paymentSet(ArrayList<Employee> employeeList) {
		Scanner sc = new Scanner(System.in);
		System.out.println("=======================================");
		System.out.println("------ RODAR FOLHA DE PAGAMENTO -------");
		System.out.println("=======================================");
		
		//cria o formatador
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		//instancia a data de hoje
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		/** A linha abaixo burla a data do Sistema para simular que hoje é uma sexta
						Serve apenas para teste  **/
		//cal.add(Calendar.DAY_OF_WEEK, n);  //n é quantos dias faltam para sexta
		
		System.out.println(dateFormat.format(cal.getTime()));
				
		//adciona 30 dias � data
		//cal.add(Calendar.DAY_OF_YEAR, 30);
		//seta cal para a classe
		//sEmployee.setPaymentDate(cal);
		//seta a data formatada para a classe
		//sEmployee.setDateFormat(dateFormat.format(cal.getTime()));
		//teste
		//System.out.println(sEmployee.getDateFormat());
		
		for(int i=0; i<employeeList.size(); ++i) {
			Employee e  = (Employee) employeeList.get(i);
			
			if(e.getDateFormat().equals(dateFormat.format(cal.getTime()))) {
				
				if(e instanceof SalariedEmployee) {
					SalariedEmployee h = (SalariedEmployee) employeeList.get(i);
					if(h.commissioned) {
						//Caso seja comissionado, divide o sal�rio fixo
						double value = h.fixedSalary/2;
						//Soma o sal�rio dividido ao montante j� acumulado
						h.totalSalary = h.totalSalary + value;
						//Imprime o nome e o valor liberado
						System.out.println("Empregado: "+h.getName());
						System.out.println("Valor Liberado : R$"+h.totalSalary);
						System.out.println("Método de Pagamento: "+h.getPaymentMethod());
						//Seta o pr�ximo pagamento para daki a 14 dias
						cal.add(Calendar.DAY_OF_YEAR, 14);
						h.setPaymentDate(cal);
						System.out.println("Próximo Pagamento: "+h.getDateFormat());
						System.out.println("=x=");
						h.setDateFormat(dateFormat.format(cal.getTime()));
						//Zera o montante acumulado
						h.totalSalary = 0;
						e = h;
					}
					else {
						//Se o empregado n�o tem comiss�es, o sal�rio total ser� somente
						//o sal�rio fixo subtraido das taxas do sindicato
						h.totalSalary = e.totalSalary + h.fixedSalary;
						System.out.println("Empregado: "+h.getName());
						System.out.println("Valor Liberado : R$"+h.totalSalary);
						System.out.println("Método de Pagamento: "+h.getPaymentMethod());
						//Seta o pr�ximo pagamento para daki a 30 dias
						cal.add(Calendar.MONTH, 1);
						cal.set(Calendar.DAY_OF_MONTH, 1);
						cal.add(Calendar.DATE, -1);
						while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
								cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
							cal.add(Calendar.DATE, -1);
							
						}
						h.setPaymentDate(cal);
						
						System.out.println("Próximo Pagamento: "+h.getDateFormat());
						System.out.println("=x=");
						h.setDateFormat(dateFormat.format(cal.getTime()));
						//Zera o montante acumulado
						h.totalSalary = 0;
						e = h;
					}
				}
				else if(e instanceof HourlyEmployee) {
					//Se o empregado � horista
					//o sal�rio � deduzido do montante acumulado
					System.out.println("Empregado: "+e.getName());
					System.out.println("Valor Liberado : R$"+e.totalSalary);
					System.out.println("Método de Pagamento: "+e.getPaymentMethod());
					
					//Seta o pr�ximo pagamento para daki a 7 dias
					cal.add(Calendar.DAY_OF_YEAR, 7);
					e.setPaymentDate(cal);
					e.setDateFormat(dateFormat.format(cal.getTime()));
					System.out.println("Próximo Pagamento: "+e.getDateFormat());
					System.out.println("=x=");
					
					
					//Zera o montante acumulado
					e.totalSalary = 0;
				}
				sc.nextLine();
			}
		}
		System.out.println("A folha foi rodada com sucesso.");
	}
	
	public static ArrayList<Employee> clone(ArrayList<Employee> list) {
		ArrayList<Employee> clone = new ArrayList<Employee>(list.size());
		for(Employee d : list) clone.add(d.clone());
		return clone;
	}
	
	public static void changeEmployee(ArrayList<Employee> employeeList, Stack stack1,
			HourlyEmployee hEmployee, SalariedEmployee sEmployee) {
		Scanner sc = new Scanner(System.in);
		String name;
		int method = 0;
		
		
		System.out.println("=================================");
		System.out.println("---- ALTERAÇÃO DE EMPREGADO -----");
		System.out.println("=================================");
		System.out.println("Digite o nome do empregado a ser alterado: ");
		name = sc.nextLine();
		
		for(int i=0; i<employeeList.size(); ++i) {
			Employee e  = employeeList.get(i);
			int choose = 0;
			
			if(e.getName().equals(name)) {
			
				boolean ready = false;
				while(!ready) {
					try {
						System.out.println("Escolha que Alteração deseja fazer: ");
						System.out.println("[1]NOME");
						System.out.println("[2]ENDEREÇO");
						System.out.println("[3]TIPO");
						System.out.println("[4]MÉTODO DE PAGAMENTO");
						System.out.println("[5]INFORMAÇÕES DO SINDICATO");
						choose = Integer.parseInt(sc.next()); 
						ready = true;
					} catch (NumberFormatException n) {
						System.out.println("FALHA!" + 
					" O Valor digitado não é um inteiro." + n.getMessage());
					}
				}
				sc.nextLine();
				
				switch(choose) {
					case 1:
						System.out.println("NOVO NOME: ");
						e.setName(sc.nextLine());
						break;
					case 2:
						System.out.println("NOVO ENDEREÇO: ");
						e.setAddress(sc.nextLine());
						break;
					case 3:
						System.out.println("=================================");
						System.out.println("------- ALTERAÇÃO DE TIPO -------");
						System.out.println("=================================");
						searchToRemove(employeeList);
						chooseType(sEmployee, hEmployee, employeeList);
						break;
					case 4:
						method = 0;
						ready = false;
						while(!ready) {
							try {
								System.out.println("MÉTODO DE PAGAMENTO DO SALÁRIO: ");
								System.out.println("[1]Cheque pelos correios.");
								System.out.println("[2]Cheque em mãos.");
								System.out.println("[3]Depósito em conta bancária.");
								method = Integer.parseInt(sc.next()); 
								ready = true;
							} catch (NumberFormatException n) {
								System.out.println("FALHA!" + 
							" O Valor digitado não é um inteiro." + n.getMessage());
							}
						}
						switch(method) {
							case 1:
								e.setPaymentMethod("Cheque pelos correios.");
								break;
							case 2:
								e.setPaymentMethod("Em mãos.");
								break;
							case 3:
								e.setPaymentMethod("Depósito em conta bancária.");
								break;
						}
						break;
					case 5:
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
								e.setUnion(true);
								System.out.println("NOME DO SINDICATO: ");
								e.setUnionName(sc.next());
								
								System.out.println("TAXA SINDICAL: ");
								e.setUnionTax(sc.nextDouble());
								break;
							case 2:
								e.setUnion(false);
								break;
						}
				}
			}
		}
	}

}
