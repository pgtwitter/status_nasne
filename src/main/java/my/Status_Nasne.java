package my;

import java.net.UnknownHostException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import jcifs.netbios.NbtAddress;

public class Status_Nasne {

	public static void main(String[] args) throws UnknownHostException {
		NbtAddress addr = NbtAddress.getByName("nasne-000000");

		String urlString = "http://" + addr.getHostAddress() + ":64210";
		Client c = ClientBuilder.newClient();
		String json = c.target(urlString).path("status/HDDInfoGet").queryParam("id", "0")
				.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);

		JSONObject hdd = (new JSONObject(json)).getJSONObject("HDD");
		double r = hdd.getDouble("usedVolumeSize") / hdd.getDouble("totalVolumeSize") * 100.0;
		System.err.println(addr.getHostName() + "\t" + String.format("%.2f%%", r));
	}
}
