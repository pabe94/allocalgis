/**
 * SatecBufferedCipherOutputStream.java
 * © MINETUR, Government of Spain
 * This program is part of LocalGIS
 * This program is licensed and may be used, modified and redistributed under the terms of the European Public License (EUPL), either version 1.1 or (at your option) any later version as soon as they are approved by the European Commission.
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and more details.
 * You should have received a copy of the EUPL1.1 license along with this program; if not, you may find it at http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 */
package es.satec.localgismobile.fw.crypto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CryptoException;

/**
 * Stream de salida con capacidad de cifrado
 * @author jpolo
 *
 */
public class SatecBufferedCipherOutputStream extends OutputStream {

	/**
	 * Objeto encargado de realizar el cifrado de los bytes
	 */
	private BufferedBlockCipher cipher;
	/**
	 * Stream de salida al que se le envian los datos ya cifrados
	 */
	private OutputStream out;
	/**
	 * ByteArrayOutputStream donde se almacenan los datos cifrados
	 * antes de ser escritos a disco
	 */
	private ByteArrayOutputStream byteArrayOut;

	/**
	 * Construye un nuevo stream de salida cifrado
	 * @param out Stream de salida donde se desean escribir los datos cifrados
	 * @param cipher Objeto encargado de cifrar los datos. Debe estar correctamente inicializado
	 */
	SatecBufferedCipherOutputStream(OutputStream out, BufferedBlockCipher cipher){
		super();
		this.out = out;
		this.byteArrayOut = new ByteArrayOutputStream();
		this.cipher = cipher;
	}
	
	/**
	 * Escribe un byte cifrado en el stream de salida
	 * @param originalData Byte sin cifrar
	 */
	public void write(int originalData) {		
		byteArrayOut.write(originalData);	
	}
	
	/**
	 * Cierra el stream de salida. Cuando se produce el cierre es el momento en el que se cifran
	 * los datos que quedan en el buffer
	 * @throws IOException Si se produce un error al escribir en el stream de salida
	 */
	public void close() throws IOException{
		byte originalBytes[];
		originalBytes = byteArrayOut.toByteArray();

		int predictedOutputSize = cipher.getOutputSize(originalBytes.length);
		byte cipheredBytes[] = new byte[predictedOutputSize];
		int outputLen = cipher.processBytes(originalBytes, 0, originalBytes.length, cipheredBytes, 0);
		if (outputLen > 0)
			out.write(cipheredBytes, 0, outputLen);
		
		try {
			outputLen = cipher.doFinal(cipheredBytes, 0);
			if (outputLen > 0)
				out.write(cipheredBytes, 0, outputLen);
		} catch (CryptoException e) {
			throw new IOException("Error al cifrar el flujo de salida");
		}

		out.close();
	}

	/**
	 * Fuerza a escribir los datos buffereados
	 * @throws IOException Si se produce un error al escribir en el stream de salida
	 */
	public void flush() throws IOException {
		return ;
	}

	/**
	 * Escribe un array de bytes cifrándolos en el stream de salida
	 * @param originalBytes Bytes que se desean escribir cifrados
	 * @param offset Offset a partir del cual comenzar a leer el array
	 * de bytes de entrada
	 */
	public void write(byte[] originalBytes, int offset, int len) {
		byteArrayOut.write(originalBytes, offset, len);
	}

	/**
	 * Escribe un array de bytes cifrándolos en el stream de salida
	 * @param originalBytes Bytes que se desean escribir cifrados
	 */
	public void write(byte[] originalBytes) throws IOException{
		byteArrayOut.write(originalBytes);
	}
}
