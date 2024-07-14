package banco.logica;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Banco {

    public static void salvarClientes(List<Cliente> clientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("clientes.dat"))) {
            oos.writeObject(clientes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Cliente> carregarClientes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("clientes.dat"))) {
            return (List<Cliente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
