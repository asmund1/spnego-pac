SPNEGO PAC
------------

Custom Spnego implementation the extracts roles from PAC using JaasLounge and 
Bouncy Castle ASN1 libraries.

ACKNOWLEDGMENTS
---------------

- The original changes to the SPENGO library was done by Ricardo Martín Camarero 
(rickyepoderi). He wrote a blog post about extracting roles from the PAC, it 
is available at http://blogs.nologin.es/rickyepoderi/index.php?/archives/73-SPNEGOKerberos-in-JavaEE-PAC.html.

- Support for compressed PAC was added using code from https://github.com/EleotleCram/jaaslounge-decoding
