/**
 * 
 */

import java.security.SecureRandom;
import java.util.Random;

/**
 * This class implements the simple XOR-based (n,n) secret sharing.
 * 
 * Secrets and shares are both represented as byte[] arrays.
 * 
 * Randomness is taken from a {@link java.security.SecureRandom} object.
 * 
 * @see SecureRandom
 * 
 * @author elmar
 * 
 */
public class XorSecretSharing {


	/**
	 * Creates a XOR secret sharing object for n shares
	 * @param n number of shares to use. Needs to fulfill n >= 2.
	 */

	public XorSecretSharing(int n) {
		assert (n >= 2);
		this.n = n;
		this.rng = new SecureRandom();
	}

	/**
	 * Shares the secret into n parts
	 * @param secret the secret to share.
	 * @return An array of the n shares.
	 */
	public byte[][] share(final byte[] secret) {
		//create shares
		byte[][] shares = new byte[n][secret.length];
		//create random bytes with rng for each n
		byte[] randomBytes = new byte[secret.length];
		//fill 1st column with random connected bytes to secret
		for(int i = 0; i < secret.length; i++){
			rng.nextBytes(randomBytes);
			shares[0][i] = (byte) (secret[i] ^ randomBytes[i]);
		}
		//fill rest with each other connected
		for(int i = 1; i < n; i++){
			rng.nextBytes(randomBytes);
			//xor the secret with the random bytes
			for(int j= 0; j < secret.length; j++){
				shares[i][j] = (shares[i-1][j] ^= randomBytes[j]);
			}
		}
		return shares;
	}

	/**
	 * Recombines the given shares into the secret.
	 * @param shares The complete set of n shares for this secret.
	 * @return The reconstructed secret.
	 */
	public byte[] combine(final byte[][] shares) {
		//xor one row and return as i in secret
		byte[] secret = new byte[shares[0].length];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < shares[0].length; j++){
				secret[i] ^= shares[i][j];
			}
		}
		return secret;
	}

	private int n;

	public int getN() {
		return n;
	}

	private Random rng;
}
