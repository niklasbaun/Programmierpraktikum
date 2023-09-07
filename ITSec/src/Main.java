public class Main {
    public static void main(String[] args) {
        //create new byte array
        byte[] secret = {1, 2, 3, 4, 5, 6, 7, 8};
        //create new XorSecretSharing with n = 6
        XorSecretSharing xss = new XorSecretSharing(6);
        //create shares
        byte[][] shares = xss.share(secret);
        //print shares
        System.out.println("Shares:");
        for (int i = 0; i < shares.length; i++) {
            System.out.print("Share " + i + ": ");
            for (int j = 0; j < shares[0].length; j++) {
                System.out.print(shares[i][j] + " ");
            }
            System.out.println();
        }
        //combine shares
        byte[] secret2 = xss.combine(shares);
        //print secret
        System.out.println("Secret:");
        for (int i = 0; i < secret2.length; i++) {
            System.out.print(secret2[i] + " ");
        }
    }
}