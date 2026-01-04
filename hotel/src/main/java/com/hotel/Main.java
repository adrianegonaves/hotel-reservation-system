package com.hotel;

import com.hotel.model.*;
import com.hotel.service.*;
import com.hotel.strategy.StandardPricingStrategy;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PricingService pricingService = new PricingService(new StandardPricingStrategy());
        BillingService billingService = new BillingService();
        ReservationService reservationService = new ReservationService();
        HousekeepingService housekeepingService = new HousekeepingService();
        
        Scanner scanner = new Scanner(System.in);
        
        Room room = new Room(101);
        room.addObserver(housekeepingService); 

        System.out.println("==========================================");
        System.out.println("   SISTEMA DE RESERVAS DE HOTEL");
        System.out.println("==========================================\n");

        System.out.print("Digite o nome do Hospede: ");
        String guestName = scanner.nextLine(); 

        System.out.print("Quantas noites deseja reservar? ");
        int nights = scanner.nextInt(); 
        scanner.nextLine(); 

        Guest guest = new Guest(guestName);

        System.out.println("\n A verificar disponibilidade do quarto...");
        
        if (room.isAvailable()) {
            System.out.println("Quarto " + room.getNumber() + " disponivel. \n A realizar reserva...");
            
            
            Reservation reservation = reservationService.reserve(guest, room, nights);
            
            System.out.println("SUCESSO: Reserva confirmada para " + reservation.getGuest().getName());
            System.out.println("STATUS ATUAL DO QUARTO: " + room.getStatus()); 
        } else {
            System.out.println("Erro: Quarto indisponível.");
            scanner.close();
            return;
        }

     
        System.out.println("\n(O hospede esta hospedado. Pressione ENTER para realizar o Check-out...)");
        scanner.nextLine(); 

        System.out.println("--- A realizar Check-out... ---");
        
      
        double amount = pricingService.calculate(nights);
        Invoice invoice = billingService.generate(amount);
        
        System.out.println("Fatura gerada: R$ " + invoice.getTotal());

        room.free(); 
        
        System.out.println("Check-out concluido.");
        System.out.println("STATUS FINAL DO QUARTO: " + room.getStatus()); 
        
        scanner.close();
    }
}