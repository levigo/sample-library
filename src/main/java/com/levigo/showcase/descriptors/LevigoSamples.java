package com.levigo.showcase.descriptors;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;

@XmlRootElement(name = "levigo-samples")
@XmlAccessorType(XmlAccessType.FIELD)
public class LevigoSamples {
  @XmlElementWrapper(name = "categories")
  @XmlElement(name = "category", type = CategoryDescriptor.class)
  protected List<CategoryDescriptor> categories;

  @XmlElement(name = "sample")
  protected List<SampleDescriptor> samples;

  public List<CategoryDescriptor> getCategories() {
    if (categories == null)
      categories = new ArrayList<CategoryDescriptor>();
    return categories;
  }

  public List<SampleDescriptor> getSamples() {
    if (samples == null)
      samples = new ArrayList<SampleDescriptor>();
    return samples;
  }

//  public static void main(String[] args) throws Throwable {
//    final JAXBContext ctx = JAXBContext.newInstance(LevigoSamples.class);
//    final Marshaller m = ctx.createMarshaller();
//    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//
//    final LevigoSamples samples = new LevigoSamples();
//    // final SampleDescriptor sample = new SampleDescriptor();
//    // sample.setName("MySimpleSample");
//    // sample.setDescription("Some useful description");
//    // sample.setSampleClass(ReadAPISample.class);
//    //
//    // final ExecutionControllerDescriptor execDesc = new ExecutionControllerDescriptor();
//    // execDesc.setMethod("myMethod");
//    // execDesc.setName("Execute!");
//    // execDesc.setDescription("Describe me!");
//    //
//    // final StartStopControllerDescriptor startStopDesc = new StartStopControllerDescriptor();
//    // startStopDesc.setName("Start/Stop the sample");
//    // startStopDesc.setDescription("There should be some useful text.");
//    // startStopDesc.setStartMethod("start");
//    // startStopDesc.setStopMethod("stop");
//    // startStopDesc.setInvocationThread(InvocationThread.EDT);
//    // sample.getControllers().add(startStopDesc);
//    // sample.getControllers().add(execDesc);
//    //
//    // sample.getClasses().add(ControllerDescriptor.class);
//    // sample.getClasses().add(ControllerDescriptor.class);
//    //
//    // // specify some resources
//    // final ResourceDescriptor res = new ResourceDescriptor();
//    // res.setPath("/path/to/some/file.properties");
//    // res.setType("text/plain");
//    // sample.getResources().add(res);
//    //
//    // final RequireSampleHostDescriptor require = new RequireSampleHostDescriptor();
//    // require.setName("jadice-documentplatform");
//    // sample.getRequirements().add(require);
//    //
//    // samples.getSamples().add(sample);
//
//    final Sample[] allSamples = new Sample[]{
//        new ReadAPISample(), new LayeredReadAPISample(), new ToolSample(), new ConditionalToolSample(),
//        new PermissionsSample(), new AnnotationsSavingSample(), new AnnotationsReadAPISample(),
//        new SimpleHelloWorldToolSample()
//    };
//
//    for (Sample s : allSamples) {
//
//      SampleDescriptor sd = convert(s);
//      samples.getSamples().add(sd);
//    }
//
//
//    final OutputStreamWriter out = new OutputStreamWriter(System.err);
//    m.marshal(samples, out);
//
//    out.flush();
//  }

//  private static SampleDescriptor convert(Sample s) {
//
//    final SampleDescriptor sd = new SampleDescriptor();
//    sd.setName(s.name());
//    sd.setSampleClass(s.getClass());
//    sd.getRequirements().add(new RequireSampleHostDescriptor("jadice-documentplatform"));
//
//    if (s instanceof StartStopSample) {
//
//      final StartStopControllerDescriptor desc = new StartStopControllerDescriptor();
//      desc.setName("Start/Stop " + s.name());
//      desc.setStartMethod("start");
//      desc.setStopMethod("stop");
//      desc.setInvocationThread(InvocationThread.EDT);
//
//      sd.getControllers().add(desc);
//    } else {
//      final ExecutionControllerDescriptor desc = new ExecutionControllerDescriptor();
//      desc.setName("Execute " + s.name());
//      desc.setMethod("execute");
//
//      sd.getControllers().add(desc);
//    }
//
//    sd.getClasses().addAll(Arrays.asList(s.getClasses()));
//
//    return sd;
//  }

  public static LevigoSamples read(InputStream is) throws JAXBException {
    // FIXME this is not failsafe!
    final JAXBContext ctx = JAXBContext.newInstance(LevigoSamples.class);

    final Unmarshaller u = ctx.createUnmarshaller();
    final JAXBElement<LevigoSamples> res = u.unmarshal(new StreamSource(is), LevigoSamples.class);
    return res.getValue();
  }
}
