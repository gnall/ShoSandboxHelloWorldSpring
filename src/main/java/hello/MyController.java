package hello;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MyController {

	private static final Logger logger = LoggerFactory.getLogger(MyController.class);

	@RequestMapping(value = "/geoLocation", method = RequestMethod.GET)
	public String geoLocationGet() {
		return "geoLoc";
	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
	public String uploadPageGet() {
		return "index";
	}
	
	@RequestMapping(value = "/fbm", method = RequestMethod.GET)
	public String fbmGet() {
		return "fbm";
	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("name") String name,
			@RequestParam("figle") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location=" + serverFile.getAbsolutePath());

				return "You successfully uploaded file=" + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}
	
}
/*

curl -X POST --header "Authorization: key=AAAAxqBnG_c:APA91bFNRJ2DQ69ZSslt1-E_Td1Mx40YLfe2ZuHq1AjF8dwMR-jJ7YA_cup7dt1mccMb0Syiukto2MBDMmho6Qecqi3Ka5P7-VpwfYPtcYMhWL1dnwNQGr5ep6PYNgMuHOmH3e5gr7FG" \
--Header "Content-Type: application/json" \
https://fcm.googleapis.com/fcm/send \
-d "{\"to\":\"cUCjdzNNlaU:APA91bHCNehjrzcmOGw4K3ziPUSxXuyMKmFXZ9d-bjehWTmXz3mLjrxcrjzWtXlCfDqS4BkH7FvTrlbgs3LCtpZsU0GIa7R_vZ6Qbk0HJ1iUuW3Bw9AZ7umk4872rayse_vZiwSsQFOi\",\"notification\":{\"body\":\"Yellow\"},\"priority\":10}"



*/